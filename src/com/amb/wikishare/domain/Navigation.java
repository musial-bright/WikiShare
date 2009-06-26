package com.amb.wikishare.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.amb.wikishare.service.ClipboardService;

/**
 * DB navigation persistence object.
 * @author amusial
 */
@Entity
@Table(name = "navigations")
public class Navigation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Integer id = -1;

    @Column(name = "name", unique = true)
    protected String name;

    @Column(name = "content")
    protected String content;

    public Navigation() {}

    public Navigation(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Navigation(NavigationFormBacking navigationForm) {
        this.id = navigationForm.getId();
        this.name = navigationForm.getName();
        this.content = navigationForm.getContent();
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


}