package com.damienfremont.tool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ReadPage {

	WebDriver driver;

	public ReadPage(WebDriver driver) {
		this.driver = driver;
	}

	String getUrl() {
		return driver.getCurrentUrl();
	}

	void isAt() {
		assertThat(imgUrl()).isNotEmpty();
		assertThat(getPageUrlList()).isNotEmpty();
	}

	List<String> getPageUrlList() {
		WebElement e = driver.findElement(By.cssSelector(".wid60"));
		List<WebElement> es = e.findElements(By.cssSelector("option"));
		return es.stream() //
				.map(i -> i.getAttribute("value")) //
				.collect(Collectors.toList());
	}

	String imgUrl() {
		return driver.findElement(By.cssSelector(".read_img img")).getAttribute("src");
	}

	String chapterTitle() {
		return driver.findElement(By.cssSelector(".title h1 a")).getText();
	}

	String serieTitle() {
		return driver.findElement(By.cssSelector(".title h2 a")).getText();
	}
}
