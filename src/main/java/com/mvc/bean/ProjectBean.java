package com.mvc.bean;

public class ProjectBean {
    private int id;
    private SpecificationBean specification;
    private int cost;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public SpecificationBean getSpecification() {
        return specification;
    }
    public void setSpecification(SpecificationBean specification) {
        this.specification = specification;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
}
