package com.amb.wikishare.domain;

public class Navigation {

	private int id = -1;
	private String name;
	private String content;

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
		this.content = content;
	}

	public boolean getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}
	
}
