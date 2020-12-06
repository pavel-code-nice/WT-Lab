package com.mvc.bean;

public class JobBean {
    private int id;
    private String name;
    private int requiredDevNumber;
    private String requiredQualification;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getRequiredDevNumber() {
        return requiredDevNumber;
    }
    public void setRequiredDevNumber(int requiredDevNumber) {
        this.requiredDevNumber = requiredDevNumber;
    }

    public String getRequiredQualification() {
        return requiredQualification;
    }
    public void setRequiredQualification(String requiredQualification) {
        this.requiredQualification = requiredQualification;
    }
}
