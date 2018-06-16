package com.damienfremont.tool.siteimpl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.damienfremont.tool.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SiteMangahere extends Site {

	public SiteMangahere(WebDriver driver) {
		super(driver);
	}

	public String serieTitle() {
		return driver.findElement(By.cssSelector("h1.title")).getText();
	}

	public List<String> chatperUrlList() {
		List<WebElement> es = driver.findElements(By.cssSelector(".detail_list .left a"));
		Collections.reverse(es);
		return es.stream() //
				.map(i -> i.getAttribute("href")) //
				.collect(Collectors.toList());
	}

	public List<String> pageUrlList() {
		WebElement e = driver.findElement(By.cssSelector(".wid60"));
		List<WebElement> es = e.findElements(By.cssSelector("option"));
		return es.stream() //
				.map(i -> i.getAttribute("value")) //
				.collect(Collectors.toList());
	}

	public String imgUrl() {
		return driver.findElement(By.cssSelector("#image")).getAttribute("src");
	}

}
