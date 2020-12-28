package com.github.kingsant.model;

import javax.persistence.*;

public class User {
    @Id
    @Column(name = "user_name")
    private String userName;

    @Column(name = "pass_word")
    private String passWord;

    private String root;

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return pass_word
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * @param passWord
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * @return root
     */
    public String getRoot() {
        return root;
    }

    /**
     * @param root
     */
    public void setRoot(String root) {
        this.root = root;
    }
}
