package com.amb.wikishare.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.amb.wikishare.service.ClipboardService;

public class PageFormBackingObject extends Page {

    // TODO: user name is needed for the wikipages (versions) view!
    protected String userName = "";


    protected boolean skipNewVersionFlag = false;

    protected int versionAmount = -1;
    protected ClipboardService clipboard = null;

    public PageFormBackingObject() {}

    public PageFormBackingObject(Page page) {
        if(page != null) {
            super.id = page.getId();
            super.signature = page.getSignature();
            super.userId = page.getUserId();
            super.activePage = page.getActivePage();
            super.frontPage = page.getFrontPage();
            super.title = page.getTitle();
            super.content = page.getContent();
            super.date = page.getDate();
        }
    }

    public int getVersionAmount() {
        return versionAmount;
    }

    public void setVersionAmount(int versionAmount) {
        this.versionAmount = versionAmount;
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


    public boolean getSkipNewVersionFlag() {
        return skipNewVersionFlag;
    }

    public void setSkipNewVersionFlag(boolean skipNewVersion) {
        this.skipNewVersionFlag = skipNewVersion;
    }


    public void setFrontPageFlag(boolean frontPage) {
        if(frontPage) {
            super.frontPage = 1;
        } else {
            super.frontPage = 0;
        }
    }

    public boolean getFrontPageFlag() {
        if(super.frontPage == 1) {
            return true;
        } else {
            return false;
        }
    }

}
