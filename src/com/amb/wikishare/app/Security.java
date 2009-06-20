package com.amb.wikishare.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.*;

public class Security {

	protected final Log logger = LogFactory.getLog(getClass());
	private final String ENCRIPTION = "SHA";
	
	public String encript(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance(ENCRIPTION);
			md.update(text.getBytes());
			//return new String(md.digest());
			byte[] messageDiggest = md.digest();
			StringBuffer hexString = new StringBuffer();
			for(int i=0; i < messageDiggest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDiggest[i]));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error("encript(): " + e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		String test = "Adam";
		Security s = new Security();
		System.out.println(test + " -> " + s.encript(test));
	}
}
