package com.damienfremont.tool;

import org.openqa.selenium.WebDriver;

public abstract class Site implements Page, PageChapter, PageSerie {

	protected WebDriver driver;

	public Site(WebDriver driver) {
		this.driver = driver;
	}

}
