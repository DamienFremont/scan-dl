package com.damienfremont.tool;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;

public class Main {

	public static void main(String[] args) {
		checkArgument(args.length > 0, "args are needed");
		String url = getUrl(args);
		checkArgument(url != null, "-url arg value must not be null");
		try {
			driver = driverInit();
			System.out.println("starting with at " + url);
			String currentUrl = url;

			int i = 1;
			while (true) {
				System.out.println("reading page " + currentUrl);
				driver.get(currentUrl);
				ReadPage page = new ReadPage(driver);
				page.isAt();
				String imgUrl = page.imgUrl();
				System.out.println("downloading img from " + imgUrl);

				String pageTitle = page.pageTitle();
				String filename = String.format("downloaded/%s/%d.jpg", pageTitle, i);
				URL fileurl = new URL(imgUrl);
				File file = new File(filename);
				FileUtils.copyURLToFile(fileurl, file);

				currentUrl = page.nextUrl();
				i++;
			}
		} catch (Exception e) {
			takeScreenshot();
			Throwables.propagate(e);
		} finally {
			driver.quit();
		}
	}

	private static void takeScreenshot() {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("screenshot_failed.png"));
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
		driver = new PhantomJSDriver(new DesiredCapabilities(ImmutableMap.of( //
				PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, //
				new PhantomJsDownloader().downloadAndExtract().getAbsolutePath())));
		driver.manage().timeouts().implicitlyWait(5, SECONDS);
		return driver;
	}

}
