package com.damienfremont.tool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MainTest {

	@Test
	public void test_args_none() {
		try {
			Main.main(new String[] {});
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).contains("args are needed");
		}
	}

	@Test
	public void test_args_url_none() {
		try {
			Main.main(new String[] { "-url" });
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).contains("-url arg value must not be null");
		}
	}

	@Test
	public void test_args_url_null() {
		try {
			Main.main(new String[] { "-url", null });
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).contains("-url arg value must not be null");
		}
	}

	@Test
	public void test_args_url_empty() {
		try {
			Main.main(new String[] { "-url", "" });
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).contains("-url arg value must not be empty");
		}
	}

	@Test
	public void test_args_url_ok() {
		Main.main(new String[] { "-url", "xxx" });
	}
}
