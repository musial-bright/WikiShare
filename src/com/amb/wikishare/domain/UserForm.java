package com.amb.wikishare.domain;

/**
 * User form backing object for the <code>UserCreateController</code>
 * @author amusial
 */
public class UserForm extends User {

    protected boolean updateUserFlag = false;

    public UserForm() {}

    public UserForm(int id) {
        super.id = id;
    }

    public UserForm(User user) {
        if (user != null) {
            super.id = user.getId();
            super.username = user.getUsername();
            super.password = user.getPassword();
        }
    }

    public void setUpdateUserFlag(boolean update) {
        this.updateUserFlag = update;
    }

    public boolean getUpdateUserFlag() {
        return this.updateUserFlag;
    }
}
