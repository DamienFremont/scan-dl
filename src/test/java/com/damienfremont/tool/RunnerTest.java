package com.damienfremont.tool;

import org.junit.Test;

public class RunnerTest {

	@Test(expected = IllegalArgumentException.class)
	public void test_wrong() {
		String url = "http://www.google.com";
		Main.main(new String[] { //
				"--url", url });
	}

	@Test
	public void test_mangahere_prequel() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/";
		String chapterIndexOverride = "0";
		Main.main(new String[] { //
				"--url", url, //
				"--chapterIndexOverride", chapterIndexOverride });
	}

	@Test
	public void test_mangahere_prequel_chap26() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/";
		String chapterIndexOverride = "0";
		String chapterIndexStart = "26";
		Main.main(new String[] { //
				"--url", url, //
				"--chapterIndexOverride", chapterIndexOverride, //
				"--chapterIndexStart", chapterIndexStart });
	}

	@Test
	public void test_mangafreak() {
		String url = "http://mangafreak.me/series1/knights-of-sidonia";
		Main.main(new String[] { //
				"--url", url });
	}
	
	@Test
	public void test_mangapark() {
		String url = "http://mangapark.me/manga/sidonia-no-kishi";
		String chapterIndexStart = "74";
		String chapterIndexOverride = "--3";
		Main.main(new String[] { //
				"--url", url, //
				"--chapterIndexOverride", chapterIndexOverride, //
				"--chapterIndexStart", chapterIndexStart });
	}
	
	@Test
	public void download_dragonball_on_japscan() {
		String url = "https://www.japscan.cc/mangas/dragon-ball-perfect-edition/";
		Main.main(new String[] { //
				"--url", url });
	}
	
}
