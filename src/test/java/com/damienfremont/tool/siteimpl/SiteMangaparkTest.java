package com.damienfremont.tool.siteimpl;

import static org.assertj.core.api.Assertions.assertThat;

import com.damienfremont.tool.MainJob;
import com.damienfremont.tool.Site;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class SiteMangaparkTest {

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
		Site p = new SiteMangapark(d);
		return p;
	}

	@Test
	public void testSerieTitle() {
		String url = "http://mangapark.me/manga/sidonia-no-kishi";
		assertThat(site(url).serieTitle()).isEqualTo("Sidonia no Kishi Manga");
	}

	@Test
	public void testChatperUrlList() {
		String url = "http://mangapark.me/manga/sidonia-no-kishi";
		assertThat(site(url).chatperUrlList()).contains( //
				"http://mangapark.me/manga/sidonia-no-kishi/s1/v1/c1/1",
				"http://mangapark.me/manga/sidonia-no-kishi/s1/v1/c2/1",
				"http://mangapark.me/manga/sidonia-no-kishi/s1/v1/c3/1");
	}

	@Test
	public void testPageUrlList() {
		String url = "http://mangapark.me/manga/sidonia-no-kishi/s1/v1/c1/1";
		assertThat(site(url).pageUrlList()).contains( //
				"http://mangapark.me/manga/sidonia-no-kishi/s1/v1/c1/1",
				"http://mangapark.me/manga/sidonia-no-kishi/s1/v1/c1/2",
				"http://mangapark.me/manga/sidonia-no-kishi/s1/v1/c1/3");
	}

	@Test
	public void testImgUrl() {
		String url = "http://mangapark.me/manga/sidonia-no-kishi/s1/v1/c1/1";
		assertThat(site(url).imgUrl()).contains(".jpg");
	}

}
