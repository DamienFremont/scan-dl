package com.damienfremont.tool;

import org.junit.Test;

public class SitesTest {

	@Test(expected = IllegalArgumentException.class)
	public void test_wrong() {
		String url = "http://www.google.com";
		Main.main(new String[] { "-url", url });
	}

	@Test
	public void test_mangahere() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/";
		String chapterIndexStartLabel = "0";
		Main.main(new String[] { "-url", url, "-chapterIndexStartLabel", chapterIndexStartLabel });
	}

	@Test
	public void test_mangafreak() {
		String url = "http://mangafreak.me/series1/knights-of-sidonia";
		Main.main(new String[] { "-url", url });
	}
}
