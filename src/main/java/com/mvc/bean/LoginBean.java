package com.mvc.bean;

public class LoginBean
{
    private String login;
    private String password;

    public String getUserName() {
        return login;
    }
    public void setUserName(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}