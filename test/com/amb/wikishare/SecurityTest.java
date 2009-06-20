package com.amb.wikishare;

import com.amb.wikishare.app.Security;

import junit.framework.TestCase;

public class SecurityTest extends TestCase {

	private Security security = new Security();
	private final String text = "Adam";
	private final String textHash = "f941e1206abd4a2d8889da67be10151f429d95dc";
	
	public void testEncription() {
		assertEquals(security.encript(text), textHash);
	}
	
}
