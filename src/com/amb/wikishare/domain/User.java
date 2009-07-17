package com.amb.wikishare.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * DB user persistence object.
 * @author amusial
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Integer id = -1;

    @Column(name = "name", unique = true)
    protected String username;

    @Column(name = "password")
    protected String password;



    public User() {}

    public User(int id) {
        this.id = id;
    }

    public User(UserFormBackingObject userForm) {
        if (userForm != null) {
            this.id = userForm.getId();
            this.username = userForm.getUsername();
            this.password = userForm.getPassword();
        }
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

}
