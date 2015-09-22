package com.damienfremont.tool;

import static org.junit.Assert.assertTrue;

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

	boolean isAt() {
		assertTrue(driver.findElement(By.cssSelector(".logo_read")).getAttribute("href")
				.contains("http://www.mangahere.co/"));
		assertTrue(img().isDisplayed());
		assertTrue(getPageList().isDisplayed());
		return true;
	}

	WebElement getPageList() {
		return driver.findElement(By.cssSelector(".wid60"));
	}

	WebElement getPageListFirst() {
		return driver.findElement(By.cssSelector(".wid60 option:first-child"));
	}

	WebElement next() {
		return driver.findElement(By.cssSelector("a.next_page"));
	}

	String nextUrl() {
		return next().getAttribute("href");
	}

	WebElement img() {
		return driver.findElement(By.cssSelector(".read_img img"));
	}

	String imgUrl() {
		return img().getAttribute("src");
	}

	String pageTitle() {
		// TODO Auto-generated method stub
		return driver.findElement(By.cssSelector(".title h1 a")).getText();
	}
}
