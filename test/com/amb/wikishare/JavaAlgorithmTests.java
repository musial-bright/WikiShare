package com.amb.wikishare;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

public class JavaAlgorithmTests extends TestCase {

	private String url = "/WikiShare/public/css/main.css"; 
	
	public void testRegularExpressionCase() {
		assertTrue(url.matches("[/.a-zA-Z1-9]*"));
		assertTrue(url.matches(url));
		assertTrue(url.matches("/WikiShare/public/css/[a-zA-Z1-9]*.css"));
	}
	
	public void testDAteFormat() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println("Date=" + date);
		System.out.println("Formated date=" + dateFormat.format(date));
	}
}
