package com.amb.wikishare.service;

import java.util.List;

import com.amb.wikishare.domain.Page;
import com.amb.wikishare.domain.PageFormBackingObject;

public interface PageInterface {

    public List<PageFormBackingObject> getPagesList(boolean activePagesOnly, boolean frontPagesOnly) throws Exception;

    /**
     * Get wiki page versions.
     * @param signature: wiki page family signature
     * @return
     * @throws Exception
     */
    public List<Page> getPageVersionsList(String signature) throws Exception;

    public Page getPage(int id) throws Exception;

    public Page getActivePageBySignature(String signature) throws Exception;

    public void savePage(Page wikipage) throws Exception;

    public void updatePage(Page wikipage) throws Exception;

    public void dropPage(Page wikipage) throws Exception;
}
