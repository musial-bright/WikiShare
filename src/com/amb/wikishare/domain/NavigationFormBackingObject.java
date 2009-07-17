package com.amb.wikishare.domain;

import java.util.List;

import com.amb.wikishare.service.ClipboardService;

public class NavigationFormBackingObject extends Navigation {

    protected ClipboardService clipboard = null;
    protected boolean updateFlag = false;

    public NavigationFormBackingObject() {}

    public NavigationFormBackingObject(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public NavigationFormBackingObject(Navigation navi) {
        if(navi != null) {
            super.id = navi.getId();
            super.name = navi.getName();
            super.content = navi.getContent();
        }
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
