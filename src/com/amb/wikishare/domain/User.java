package com.amb.wikishare.domain;

public class User {
	
	private int id = -1;
	private String username;
	private String password;
	
	private boolean updateUserFlag = false;
	
	public User() {}
	
	public User(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public void setUpdateUserFlag(boolean update) {
		this.updateUserFlag = update;
	}
	
	public boolean getUpdateUserFlag() {
		return this.updateUserFlag;
	}
}
