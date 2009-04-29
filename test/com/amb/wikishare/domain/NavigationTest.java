package com.amb.wikishare.domain;

import junit.framework.TestCase;

public class NavigationTest extends TestCase {

	
	public void testEscapeSingleTicksInContent() {
		Navigation navi = new Navigation();
		navi.setContent("word1'word2");
		System.out.println("Navi content = " + navi.getContent());
		assertTrue(navi.getContent().equals("word1&#39;word2"));
	}
	
	public void testEscapeQuotationMarksContent() {
		Navigation navi = new Navigation();
		navi.setContent("word1\"word2");
		System.out.println("Navi content = " + navi.getContent());
		assertTrue(navi.getContent().equals("word1&quot;word2"));
	}
}
