package com.amb.wikishare.service;

import java.util.List;

import com.amb.wikishare.domain.Navigation;
import com.amb.wikishare.domain.User;

public interface NavigationInterface {

	public List<Navigation> getNavigationsList() throws Exception;
	
	public Navigation getNavigation(int id) throws Exception;
	
	public void saveNavigation(Navigation Navigation) throws Exception;
	
	public void updateNavigation(Navigation Navigation) throws Exception;
	
	public void dropNavigation(Navigation Navigation) throws Exception;
}
