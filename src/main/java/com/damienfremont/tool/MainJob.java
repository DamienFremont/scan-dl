package com.damienfremont.tool;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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

public class MainJob {

	private WebDriver driver;

	void execute(String url, String target, int chapterIndexOverride, int chapterIndexStart) {
		try {
			driver = driverInit();
			System.out.println("starting at " + url);

			// SERIE

			driver.get(url);
			PageSerie serie = siteFactory(url);
			String serieTitle = serie.serieTitle();
			List<String> chatperUrlList = serie.chatperUrlList();
			System.out.println("reading serie " + serieTitle + " count =  " + chatperUrlList.size());

			// CHAPTER LIST

			int iChapter = chapterIndexStart;
			while (iChapter < chatperUrlList.size()) {
				String chapterUrl = chatperUrlList.get(iChapter);
				try {

					// CHAPTER

					driver.get(chapterUrl);
					PageChapter chapter = siteFactory(chapterUrl);
					List<String> pageUrlList = chapter.pageUrlList();
					System.out.println("reading chapter " + (iChapter + 1) + " with page count = " + pageUrlList.size()
							+ " from " + chapterUrl);

					// PAGE LIST

					int iPage = 0;
					while (iPage < pageUrlList.size()) {
						String pageUrl = pageUrlList.get(iPage);

						// PAGE

						try {

							String fileName = formatFileName(target, serieTitle, iChapter + chapterIndexOverride,
									iPage + 1);
							downloadImg(pageUrl, fileName);
							iPage++;

						} catch (UnreachableBrowserException e) {
							System.out.println("relaunching webdriver (UnreachableBrowserException)");
							driver.quit();
							driver = driverInit();
						}
					}
					iChapter++;

				} catch (UnreachableBrowserException e) {
					System.out.println("relaunching webdriver (UnreachableBrowserException)");
					driver.quit();
					driver = driverInit();
				}
			}
			System.out.println("ending at " + url);
		} catch (Exception e) {
			takeScreenshot(target);
			System.out.println("error at " + url);
			Throwables.propagate(e);
		} finally {
			driver.quit();
		}
	}

	private Site siteFactory(String siteUrl) {
		if (siteUrl.contains("mangahere"))
			return new SiteMangahere(driver);
		else if (siteUrl.contains("mangafreak"))
			return new SiteMangafreak(driver);
		else if (siteUrl.contains("mangapark"))
			return new SiteMangapark(driver);
		throw new IllegalArgumentException(
				"This website is not supported by this tool. Try instead: mangahere, mangafreak, mangapark");
	}

	private String formatFileName(String target, String serieTitle, int iChapter, int iPage) {
		String chapter = format("%1$03d", iChapter);
		String page = format("%1$03d", iPage);
		String fileName = format("%s/%s-chapter_%s-page_%s.jpg", target, serieTitle, chapter, page);
		return fileName;
	}

	private void downloadImg(String pageUrl, String fileName) throws IOException, MalformedURLException {
		driver.get(pageUrl);
		Page page = siteFactory(pageUrl);
		String imgUrl = page.imgUrl();
		System.out.println(String.format("saving %s from img %s at %s", fileName, imgUrl, pageUrl));
		URL url = new URL(imgUrl);
		File file = new File(fileName);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
		conn.connect();
		FileUtils.copyInputStreamToFile(conn.getInputStream(), file);
	}

	private void takeScreenshot(String target) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(target + "/screenshot_failed.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	WebDriver driverInit() {
		driver = new PhantomJSDriver(
				new DesiredCapabilities(ImmutableMap.of(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
						new PhantomJsDownloader().downloadAndExtract().getAbsolutePath())));
		driver.manage().timeouts().implicitlyWait(10, SECONDS);
		return driver;
	}
}
