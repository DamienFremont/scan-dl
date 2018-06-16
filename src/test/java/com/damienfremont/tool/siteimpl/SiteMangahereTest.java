package com.damienfremont.tool.siteimpl;

import static org.assertj.core.api.Assertions.assertThat;

import com.damienfremont.tool.MainJob;
import com.damienfremont.tool.Site;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class SiteMangahereTest {

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
		Site p = new SiteMangahere(d);
		return p;
	}

	@Test
	public void testSerieTitle() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/";
		assertThat(site(url).serieTitle()).isEqualTo("SIDONIA NO KISHI");
	}

	@Test
	public void testChatperUrlList() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/";
		assertThat(site(url).chatperUrlList()).contains( //
				"http://www.mangahere.co/manga/sidonia_no_kishi/v01/c000/",
				"http://www.mangahere.co/manga/sidonia_no_kishi/v01/c001/",
				"http://www.mangahere.co/manga/sidonia_no_kishi/v01/c002/");
	}

	@Test
	public void testPageUrlList() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/v08/c077/";
		assertThat(site(url).pageUrlList()).contains( //
				"http://www.mangahere.co/manga/sidonia_no_kishi/v08/c077/",
				"http://www.mangahere.co/manga/sidonia_no_kishi/v08/c077/2.html",
				"http://www.mangahere.co/manga/sidonia_no_kishi/v08/c077/3.html");
	}

	@Test
	public void testImgUrl() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/v08/c077/2.html";
		assertThat(site(url).imgUrl()).contains(".jpg");
	}

}
