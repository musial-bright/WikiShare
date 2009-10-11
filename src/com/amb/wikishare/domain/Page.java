package com.amb.wikishare.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.amb.wikishare.service.ClipboardService;

@Entity
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Integer id = -1;

    // Signature is the wiki page family. All versions of the same document
    // have the same signature!
    @Column(name = "signature")
    protected String signature = null;

    @Column(name = "user_id")
    protected Integer userId = -1;

    @Column(name = "active_page")
    protected Integer activePage = 1;

    @Column(name = "front_page")
    protected Integer frontPage = 0;

    @Column(name = "title")
    protected String title = null;

    @Column(name = "content")
    protected String content = null;

    @Column(name = "timestamp")
    protected Date date = null;



    public Page() {
    }

    public Page(int id) {
        this.id = id;
    }

    public Page(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Page(PageFormBackingObject wikipageForm) {
        if(wikipageForm != null) {
            this.id = wikipageForm.getId();
            this.signature = wikipageForm.getSignature();
            this.userId = wikipageForm.getUserId();
            this.activePage = wikipageForm.getActivePage();
            this.frontPage = wikipageForm.getFrontPage();
            this.title = wikipageForm.getTitle();
            this.content = wikipageForm.getContent();
            this.date = wikipageForm.getDate();
        }
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * A wiki page entry needs always a signature.
     * The signature tells the wiki page, to which family (version of the same document)
     * it belongs. If a wiki page will be updated, the signature should
     * be taken from the original wiki page version.
     * @return
     */
    public String getSignature() {
        if(signature == null) {
            int sig = Math.abs(new Random().nextInt());
            this.signature = "s"+ sig;
        }
        return signature;
    }

    /**
     * In case a wiki page will be updated - set the signature from the
     * previous wiki page version.
     * @param signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivePage() {
        return activePage;
    }

    public void setActivePage(boolean activePage) {
        if(activePage) {
            this.activePage = 1;
        } else {
            this.activePage = 0;
        }
    }

    public void setActivePage(int activePage) {
        this.activePage = activePage;
    }

    public Integer getFrontPage() {
        return frontPage;
    }

    public void setFrontPage(boolean frontPage) {
        if(frontPage) {
            this.frontPage = 1;
        } else {
            this.frontPage = 0;
        }
    }

    public void setFrontPage(int frontPage) {
        this.frontPage = frontPage;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return this.date;
    }
    public String getDateForHuman() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(getDate());
    }

}