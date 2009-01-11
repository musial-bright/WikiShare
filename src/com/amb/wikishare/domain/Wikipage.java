package com.amb.wikishare.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Wikipage {

	private int id = -1;
	
	// Signature is the wiki page family. All versions of the same document
	// have the same signature!
	private String signature = null;
	private boolean activePage = true;
	private boolean frontPage = false;
	private String title = null;
	private String content = null;
	private Date date = null;
	private int versionAmount = -1;
	private User user = null;
	private ArrayList<WikiFile> files = new ArrayList<WikiFile>(); 
	
	// Request flags
	private boolean skipNewVersionFlag = false;

	public Wikipage() {
	}
	
	public Wikipage(int id) {
		this.id = id; 
	}
	
	public Wikipage(int id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * A wiki page entry needs always a signature.
	 * The signature tells the wiki page, to which family (version of the same document)
	 * it belongs. If a wiki page will be updated, the signature should
	 * be taken from the original wiki page version. 
	 * @return
	 */
	public String getSignature() {
		if(signature == null) {
			Double sig = new Double((new Random()).nextDouble());
			this.signature = sig.toString();
		}
		return signature;
	}

	/**
	 * In case a wiki page will be updated - set the signature from the 
	 * previous wiki page version.
	 * @param signature
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public boolean getActivePage() {
		return activePage;
	}

	public void setActivePage(boolean activePage) {
		this.activePage = activePage;
	}
	
	public void setActivePage(int activePage) {
		if(activePage == 1) {
			this.activePage = true;
		} else {
			this.activePage = false;
		}
	}
	
	public boolean getFrontPage() {
		return frontPage;
	}

	public void setFrontPage(boolean frontPage) {
		this.frontPage = frontPage;
	}
	
	public void setFrontPage(int frontPage) {
		if(frontPage == 1) {
			this.frontPage = true;
		} else {
			this.frontPage = false;
		}
	}

	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return this.date;
	}

	public boolean getSkipNewVersionFlag() {
		return skipNewVersionFlag;
	}

	public void setSkipNewVersionFlag(boolean skipNewVersion) {
		this.skipNewVersionFlag = skipNewVersion;
	}
	
	public int getVersionAmount() {
		return versionAmount;
	}

	public void setVersionAmount(int versionAmount) {
		this.versionAmount = versionAmount;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setFiles(ArrayList<WikiFile> imagePaths) {
		this.files = imagePaths;
	}
	public ArrayList<WikiFile> getFiles() {
		return this.files;
	}
	public void appendFile(WikiFile file) {
		this.files.add(file);
	}
	
}
