package com.damienfremont.tool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class SiteMangahereTest {

	private Site site(String url) {
		WebDriver d = new MainJob().driverInit();
		d.get(url);
		Site p = new SiteMangahere(d);
		return p;
	}

	@Test
	public void testSerieTitle() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/v01/c000/";
		assertThat(site(url).serieTitle()).isEqualTo("Sidonia no Kishi Manga");
	}

	@Test
	public void testChatperUrlList() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/v01/c000/";
		assertThat(site(url).chatperUrlList()).isNotEmpty().contains(
				"http://www.mangahere.co/manga/sidonia_no_kishi/v01/c000/",
				"http://www.mangahere.co/manga/sidonia_no_kishi/v01/c001/",
				"http://www.mangahere.co/manga/sidonia_no_kishi/v01/c002/");
	}

	@Test
	public void testChapterTitle() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/v01/c000/";
		assertThat(site(url).chapterTitle()).isEqualTo("Vol 01 Ch 000: Prequel Story [BD pamphlet]");
	}

	@Test
	public void testPageUrlList() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/v01/c000/";
		assertThat(site(url).pageUrlList()).isNotEmpty().contains(
				"http://www.mangahere.co/manga/sidonia_no_kishi/v08/c077/",
				"http://www.mangahere.co/manga/sidonia_no_kishi/v08/c077/2.html",
				"http://www.mangahere.co/manga/sidonia_no_kishi/v08/c077/3.html");
	}

	@Test
	public void testImgUrl() {
		fail("Not yet implemented");
	}

}
