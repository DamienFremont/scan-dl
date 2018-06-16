package com.damienfremont.tool.siteimpl;

import java.util.List;
import java.util.stream.Collectors;

import com.damienfremont.tool.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SiteMangafreak extends Site {

	public SiteMangafreak(WebDriver driver) {
		super(driver);
	}

	public String serieTitle() {
		return driver.findElement(By.cssSelector("h5.aname")).getText();
	}

	public List<String> chatperUrlList() {
		List<WebElement> es = driver.findElements(By.cssSelector("#listing a"));
		return es.stream() //
				.map(i -> i.getAttribute("href")) //
				.collect(Collectors.toList());
	}

	public List<String> pageUrlList() {
		WebElement e = driver.findElement(By.cssSelector("#pageMenu"));
		String baseUrl = driver.getCurrentUrl().split("/")[0] + "//" + driver.getCurrentUrl().split("/")[2];
		List<WebElement> es = e.findElements(By.cssSelector("option"));
		return es.stream() //
				.map(i -> baseUrl + i.getAttribute("value")) //
				.collect(Collectors.toList());
	}

	public String imgUrl() {
		return driver.findElement(By.cssSelector("#img")).getAttribute("src");
	}
}
