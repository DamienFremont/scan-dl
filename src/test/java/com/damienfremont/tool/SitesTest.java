package com.damienfremont.tool;

import org.junit.Ignore;
import org.junit.Test;

public class SitesTest {
	
	@Ignore
	@Test
	public void test_local() {
		String url = "localbook";
		Main.main(new String[]{"-url",url});
	}
	
	@Test
	public void test_wrong() {
		String url = "http://www.google.com";
		Main.main(new String[]{"-url",url});
	}
	
	@Test
	public void test_mangahere() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/v01/c000/";
		String chapterIndexStartLabel = "0";
		Main.main(new String[]{"-url",url, "-chapterIndexStartLabel",chapterIndexStartLabel});
	}
	
	@Ignore
	@Test
	public void test_mangafreak() {
		String url = "http://mangafreak.me/read1/knights-of-sidonia/1";
		Main.main(new String[]{"-url",url});
	}
}
