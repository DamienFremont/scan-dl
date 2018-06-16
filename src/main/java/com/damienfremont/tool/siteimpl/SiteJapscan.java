package com.damienfremont.tool.siteimpl;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.damienfremont.tool.Site;

public class SiteJapscan  extends Site {

	public SiteJapscan(WebDriver driver) {
		super(driver);
	}

	public String serieTitle() {
		return driver.findElement(By.cssSelector("div.content > h1.bg-header > a")).getText();
	}

	public List<String> chatperUrlList() {
		List<WebElement> es = driver.findElements(By.cssSelector("#liste_chapitres > ul > li > a"));
		return es.stream() //
				.map(i -> i.getAttribute("href")) //
				.collect(Collectors.toList());
	}

	public List<String> pageUrlList() {
		WebElement e = driver.findElement(By.cssSelector("#pages"));
		String baseUrl = driver.getCurrentUrl().split("/")[0] + "//" + driver.getCurrentUrl().split("/")[2];
		List<WebElement> es = e.findElements(By.cssSelector("option"));
		return es.stream() //
				.map(i -> baseUrl + i.getAttribute("value")) //
				.collect(Collectors.toList());
	}

	public String imgUrl() {
		return driver.findElement(By.cssSelector("#image")).getAttribute("src");
	}
}