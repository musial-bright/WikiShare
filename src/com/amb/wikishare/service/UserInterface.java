package com.amb.wikishare.service;

import java.util.List;

import com.amb.wikishare.domain.User;

public interface UserInterface {

	public List<User> getUsersList() throws Exception;
	
	public User getUser(int id) throws Exception;
	
	public void saveUser(User user) throws Exception;
	
	public void updateUser(User user) throws Exception;
	
	public void dropUser(User user) throws Exception;
}
