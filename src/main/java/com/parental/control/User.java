package com.parental.control;

public class User {
    private boolean checkIfUserAlreadyExists;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private boolean checkIfEmailAlreadyExists;
    private boolean checkIfWrongUserPasswordCombination;

    private String gmail;

    private String app_password;

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getApp_password() {
        return app_password;
    }

    public void setApp_password(String app_password) {
        this.app_password = app_password;
    }

    private String plan;

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public boolean isCheckIfWrongUserPasswordCombination() {
        return checkIfWrongUserPasswordCombination;
    }

    public void setCheckIfWrongUserPasswordCombination(boolean checkIfWrongUserPasswordCombination) {
        this.checkIfWrongUserPasswordCombination = checkIfWrongUserPasswordCombination;
    }

    public boolean isCheckIfEmailAlreadyExists() {
        return checkIfEmailAlreadyExists;
    }

    public void setCheckIfEmailAlreadyExists(boolean checkIfEmailAlreadyExists) {
        this.checkIfEmailAlreadyExists = checkIfEmailAlreadyExists;
    }

    public boolean isCheckIfUserAlreadyExists() {
        return checkIfUserAlreadyExists;
    }

    public void setCheckIfUserAlreadyExists(boolean checkIfUserAlreadyExists) {
        this.checkIfUserAlreadyExists = checkIfUserAlreadyExists;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return "username: "+this.username+", firstname: "+this.firstname+", lastname: "+this.lastname+", email: "+this.email+", password: "+this.password+", plan: "+this.plan;
    }
}
