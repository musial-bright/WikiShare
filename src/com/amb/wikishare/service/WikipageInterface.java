package com.amb.wikishare.service;

import java.util.List;

import com.amb.wikishare.domain.Page;
import com.amb.wikishare.domain.PageFormBackingObject;

public interface WikipageInterface {

    public List<PageFormBackingObject> getWikipagesList(boolean activePagesOnly, boolean frontPagesOnly) throws Exception;

    /**
     * Get wiki page versions.
     * @param signature: wiki page family signature
     * @return
     * @throws Exception
     */
    public List<Page> getWikipageVersionsList(String signature) throws Exception;

    public Page getPage(int id) throws Exception;

    public Page getActivePageBySignature(String signature) throws Exception;

    public void saveWikipage(Page wikipage) throws Exception;

    public void updateWikipage(Page wikipage) throws Exception;

    public void dropWikipage(Page wikipage) throws Exception;
}
