package com.damienfremont.tool;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SiteMangahere extends Site {

	public SiteMangahere(WebDriver driver) {
		super(driver);
	}

	// SERIE

	public String serieTitle() {
		return driver.findElement(By.cssSelector(".title h2 a")).getText();
	}

	public List<String> chatperUrlList() {
		WebElement e = driver.findElement(By.cssSelector("#top_chapter_list"));
		List<WebElement> es = e.findElements(By.cssSelector("option"));
		return es.stream() //
				.map(i -> i.getAttribute("value")) //
				.collect(Collectors.toList());
	}

	// CHAPTER

	public String chapterTitle() {
		return driver.findElement(By.cssSelector(".title h1 a")).getText();
	}

	public List<String> pageUrlList() {
		WebElement e = driver.findElement(By.cssSelector(".wid60"));
		List<WebElement> es = e.findElements(By.cssSelector("option"));
		return es.stream() //
				.map(i -> i.getAttribute("value")) //
				.collect(Collectors.toList());
	}

	// PAGE

	public String imgUrl() {
		return driver.findElement(By.cssSelector(".read_img img")).getAttribute("src");
	}

}
