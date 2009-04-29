package com.amb.wikishare.domain;

import java.util.List;

import com.amb.wikishare.service.ClipboardService;

public class Navigation {

	private int id = -1;
	private String name;
	private String content;
	private ClipboardService clipboard = null;

	private boolean updateFlag = false;
	
	public Navigation() {}
	
	public Navigation(String name, String content) {
		this.name = name;
		this.content = content;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		content = content.replaceAll("\"", "&quot;");
		content = content.replaceAll("'", "&#39;");
		this.content = content;
	}

	public boolean getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}
	
	public ClipboardService getClipboard() {
		return clipboard;
	}

	public void setClipboard(ClipboardService clipboard) {
		this.clipboard = clipboard;
	}	
	
	public void setClipboardItems(List<String> clipboardItems) {
		this.clipboard.clearClipboard();
		for(String item : clipboardItems) {
			clipboard.addClipboard(item);
		}
	}
	
	public List<String> getClipboardItems() {
		return clipboard.getClipboardList();
	}
}