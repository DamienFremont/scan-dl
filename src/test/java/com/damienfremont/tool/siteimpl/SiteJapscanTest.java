package com.damienfremont.tool.siteimpl;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import com.damienfremont.tool.MainJob;
import com.damienfremont.tool.Site;

public class SiteJapscanTest {

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
        Site p = new SiteJapscan(d);
        return p;
    }

    @Test
    public void testSerieTitle() {
        String url = "https://www.japscan.cc/mangas/dragon-ball-perfect-edition/";
        assertThat(site(url).serieTitle()).isEqualTo("Manga Dragon Ball - Perfect Edition VF");
    }

	@Test
	public void testChatperUrlList() {
		String url = "https://www.japscan.cc/mangas/dragon-ball-perfect-edition/";
		assertThat(site(url).chatperUrlList()).contains( //
				"https://www.japscan.cc/lecture-en-ligne/dragon-ball-perfect-edition/volume-1/", //
				"https://www.japscan.cc/lecture-en-ligne/dragon-ball-perfect-edition/volume-2/", //
				"https://www.japscan.cc/lecture-en-ligne/dragon-ball-perfect-edition/volume-3/");
	}

	@Test
	public void testPageUrlList() {
		String url = "https://www.japscan.cc/lecture-en-ligne/dragon-ball-perfect-edition/volume-1/";
		assertThat(site(url).pageUrlList()).contains( //
				"https://www.japscan.cc/lecture-en-ligne/dragon-ball-perfect-edition/volume-1/01.html", //
				"https://www.japscan.cc/lecture-en-ligne/dragon-ball-perfect-edition/volume-1/02.html",
				"https://www.japscan.cc/lecture-en-ligne/dragon-ball-perfect-edition/volume-1/03.html");
	}

    @Test
    public void testImgUrl() {
        String url = "https://www.japscan.cc/lecture-en-ligne/dragon-ball-perfect-edition/volume-1/2.html";
        assertThat(site(url).imgUrl()).contains(".jpg");
    }
}
