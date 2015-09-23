package com.damienfremont.tool;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;

public class Main {

	public static void main(String[] args) {
		checkArgument(args.length > 0, "args are needed");
		String serieUrl = getUrl(args);
		checkArgument(serieUrl != null, "-url arg value must not be null");

		String target = target();

		try {
			driver = driverInit();
			System.out.println("starting at " + serieUrl);

			// SERIE
			
			driver.get(serieUrl);
			ReadPage serie = new ReadPage(driver);
			serie.isAt();
			String serieTitle = serie.serieTitle();
			List<String> chatperUrlList = serie.getChatperUrlList();
			System.out.println("reading serie " + serieTitle + " count =  " + chatperUrlList.size());

			// CHAPTER LIST

			int iChapter = 0;
			while (iChapter < chatperUrlList.size()) {
				String chapterUrl = chatperUrlList.get(iChapter);
				try {

					// CHAPTER

					driver.get(chapterUrl);
					ReadPage chapter = new ReadPage(driver);
					chapter.isAt();
					String chapterTitle = chapter.chapterTitle();
					List<String> pageUrlList = chapter.getPageUrlList();
					System.out.println("reading chapter " + iChapter+1 + " : " + chapterTitle + " with page count = "
							+ pageUrlList.size() + " from " + chapterUrl);

					// PAGE LIST

					int iPage = 0;
					while (iPage < pageUrlList.size()) {
						String pageUrl = pageUrlList.get(iPage);

						// PAGE

						try {

							String fileName = formatFileName(target, serieTitle, iChapter+1, iPage+1);
							downloadImg(pageUrl, fileName);
							iPage++;

						} catch (UnreachableBrowserException e) {
							System.out.println("relaunching webdriver (UnreachableBrowserException)");
							driver = driverInit();
						}
					}
					iChapter++;

				} catch (UnreachableBrowserException e) {
					System.out.println("relaunching webdriver (UnreachableBrowserException)");
					driver = driverInit();
				}
			}
		} catch (Exception e) {
			takeScreenshot(target);
			Throwables.propagate(e);
		} finally {
			driver.quit();
		}
	}

	private static String target() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String ts = sdf.format(new Date());
		String target = String.format("target/%s", ts);
		return target;
	}

	private static String formatFileName(String target, String serieTitle, int iChapter, int iPage) {
		String chapter = format("%1$03d", iChapter);
		String page = format("%1$03d", iPage);
		String fileName = format("%s/%s-chapter_%s-page_%s.jpg", target, serieTitle, chapter, page);
		return fileName;
	}

	private static void downloadImg(String sourcePageUrl, String targetFileName)
			throws IOException, MalformedURLException {
		driver.get(sourcePageUrl);
		ReadPage page = new ReadPage(driver);
		page.isAt();
		String imgUrl = page.imgUrl();
		System.out.println(String.format("saving %s from img %s at %s", targetFileName, imgUrl, sourcePageUrl));
		FileUtils.copyURLToFile(new URL(imgUrl), new File(targetFileName));
	}

	private static void takeScreenshot(String target) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(target + "/screenshot_failed.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getUrl(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if ("-url".equals(arg)) {
				int iValue = i + 1;
				checkArgument(args.length > iValue, "-url arg value must not be null");
				String url = args[iValue];
				checkArgument(url != null, "-url arg value must not be null");
				checkArgument(!url.isEmpty(), "-url arg value must not be empty");
				return url;
			}
		}
		return null;
	}

	private static WebDriver driver;

	private static WebDriver driverInit() {
		driver = new PhantomJSDriver(
				new DesiredCapabilities(ImmutableMap.of(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
						new PhantomJsDownloader().downloadAndExtract().getAbsolutePath())));
		driver.manage().timeouts().implicitlyWait(10, SECONDS);
		return driver;
	}

}
