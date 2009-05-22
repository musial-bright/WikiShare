package com.amb.wikishare.service;

import java.util.List;

import com.amb.wikishare.domain.Wikipage;

public interface WikipageInterface {

	public List<Wikipage> getWikipagesList(boolean activePagesOnly, boolean frontPagesOnly) throws Exception;
	
	/**
	 * Get wiki page versions.
	 * @param signature: wiki page family signature
	 * @return
	 * @throws Exception
	 */
	public List<Wikipage> getWikipageVersionsList(String signature) throws Exception;
	
	public Wikipage getPage(int id) throws Exception;
	
	public Wikipage getActivePageBySignature(String signature) throws Exception;
	
	public void saveWikipage(Wikipage wikipage) throws Exception;
	
	public void updateWikipage(Wikipage wikipage) throws Exception;
	
	public void dropWikipage(Wikipage wikipage) throws Exception;
}
