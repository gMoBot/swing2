package com.swing2.example.model;

/**
 * Created by garrettcoggon on 6/26/15.
 */
public enum  EmploymentCategory {
    employed("employed"),
    selfEmployed("self employed"),
    unemployed("unemployed"),
    other("other");

    private String text;

    private EmploymentCategory(String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
