package com.vpmsbcm.common.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testSplitIdEasy() {
		assertEquals("1", Util.splitId("abc^1"));
	}

	@Test
	public void testSplitIdWithEndingDelimeter() {
		assertEquals("1", Util.splitId("abc^1^"));
	}

	@Test
	public void testSplitIdLong() {
		assertEquals("123", Util.splitId("abc^1^123"));
	}

	@Test
	public void testSplitIdEmpty() {
		assertEquals("", Util.splitId(""));
	}
}
