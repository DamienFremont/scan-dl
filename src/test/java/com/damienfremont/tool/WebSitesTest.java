package com.damienfremont.tool;

import org.junit.Ignore;
import org.junit.Test;

public class WebSitesTest {
	
	@Ignore
	@Test
	public void test_local() {
		String url = "localbook";
		Main.main(new String[]{"-url",url});
		
	}
	
	@Test
	public void test_mangahere() {
		String url = "http://www.mangahere.co/manga/sidonia_no_kishi/v01/c000/";
		Main.main(new String[]{"-url",url});
		
	}
	
	@Ignore
	@Test
	public void test_mangafreak() {
		String url = "http://mangafreak.me/read1/knights-of-sidonia/1";
		Main.main(new String[]{"-url",url});
		
	}
}
