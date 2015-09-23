package com.damienfremont.tool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class SiteMangafreakTest {

	WebDriver d;

	@Before
	public void before() {
		d = new MainJob().driverInit();
	}

	@After
	public void after() {
		d.quit();
	}

	private Site site(String url) {
		d.get(url);
		Site p = new SiteMangafreak(d);
		return p;
	}

	@Test
	public void testSerieTitle() {
		String url = "http://mangafreak.me/series1/knights-of-sidonia";
		assertThat(site(url).serieTitle()).isEqualTo("KNIGHTS 0F SIDONIA");
	}

	@Test
	public void testChatperUrlList() {
		String url = "http://mangafreak.me/series1/knights-of-sidonia";
		assertThat(site(url).chatperUrlList()).contains( //
				"http://mangafreak.me/read1/knights-of-sidonia/1", //
				"http://mangafreak.me/read1/knights-of-sidonia/2", //
				"http://mangafreak.me/read1/knights-of-sidonia/3");
	}

	@Test
	public void testPageUrlList() {
		String url = "http://mangafreak.me/read1/knights-of-sidonia/1";
		assertThat(site(url).pageUrlList()).contains( //
				"http://mangafreak.me/read1/knights-of-sidonia/1", //
				"http://mangafreak.me/read1/knights-of-sidonia/1/2",
				"http://mangafreak.me/read1/knights-of-sidonia/1/3");
	}

	@Test
	public void testImgUrl() {
		String url = "http://mangafreak.me/read1/knights-of-sidonia/1/2";
		assertThat(site(url).imgUrl()).contains(".jpg");
	}

}
