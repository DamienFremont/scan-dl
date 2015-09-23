package com.damienfremont.tool;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SiteMangapark extends Site {

	public SiteMangapark(WebDriver driver) {
		super(driver);
	}

	public String serieTitle() {
		return driver.findElement(By.cssSelector("h1")).getText();
	}

	public List<String> chatperUrlList() {
		List<WebElement> es = driver.findElements(By.cssSelector("#stream_1 a.ch.sts"));
		Collections.reverse(es);
		return es.stream() //
				.map(i -> i.getAttribute("href")) //
				.collect(Collectors.toList());
	}

	public List<String> pageUrlList() {
		WebElement e = driver.findElement(By.cssSelector("#sel_page_1"));
		String baseUrl = driver.getCurrentUrl().split("/")[0] //
				+ "//" + driver.getCurrentUrl().split("/")[2] //
				+ "/" + driver.getCurrentUrl().split("/")[3] //
				+ "/" + driver.getCurrentUrl().split("/")[4] //
				+ "/" + driver.getCurrentUrl().split("/")[5] //
				+ "/" + driver.getCurrentUrl().split("/")[6] //
				+ "/" + driver.getCurrentUrl().split("/")[7];
		List<WebElement> es = e.findElements(By.cssSelector("option"));
		return es.stream() //
				.map(i -> baseUrl + i.getAttribute("value")) //
				.collect(Collectors.toList());
	}

	public String imgUrl() {
		return driver.findElement(By.cssSelector("#img-1")).getAttribute("src");
	}

}
