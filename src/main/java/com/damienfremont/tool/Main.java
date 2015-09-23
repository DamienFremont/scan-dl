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
		String url = getUrl(args);
		checkArgument(url != null, "-url arg value must not be null");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String ts = sdf.format(new Date());
		String target = String.format("target/%s", ts);

		try {
			driver = driverInit();
			System.out.println("starting at " + url);

			driver.get(url);
			ReadPage chapter = new ReadPage(driver);
			chapter.isAt();

			// SERIE
			
			String serieTitle = chapter.serieTitle();
			System.out.println("reading serie " + serieTitle);
			
			// CHAPTER
			
			System.out.println("reading chapter " + chapter.chapterTitle());
			System.out.println("chapter page count = " + chapter.getPageUrlList().size());

			String fileNamePattern = target + "/" + serieTitle + "-%s-" + chapter.chapterTitle() + ".jpg";

			int i = 1;
			for (String pageUrl : chapter.getPageUrlList()) {
				
				// PAGE
				
				try {
					String fileName = format(fileNamePattern, format("%1$03d", i));
					downloadImg(pageUrl, fileName);
					i++;
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

	private static void downloadImg(String sourcePageUrl, String targetFileName)
			throws IOException, MalformedURLException {
		driver.get(sourcePageUrl);
		ReadPage page = new ReadPage(driver);
		page.isAt();
		String imgUrl = page.imgUrl();
		System.out.println(String.format("saving img %s from %s to %s", imgUrl, sourcePageUrl, targetFileName));
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
