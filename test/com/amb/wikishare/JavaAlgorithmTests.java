package com.amb.wikishare;

import junit.framework.TestCase;

public class JavaAlgorithmTests extends TestCase {

	private String url = "/WikiShare/public/css/main.css"; 
	
	public void testRegularExpression() {
		assertTrue(url.matches("[/.a-zA-Z1-9]*"));
		assertTrue(url.matches(url));
		assertTrue(url.matches("/WikiShare/public/css/[a-zA-Z1-9]*.css"));
	}
}
