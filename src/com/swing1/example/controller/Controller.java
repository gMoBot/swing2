package com.swing1.example.controller;

import com.swing1.example.gui.FormEvent;
import com.swing1.example.model.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by garrettcoggon on 6/26/15.
 */
public class Controller {
    // Could also use a method which accepts data from form event rather than form event itself.

    Database db = new Database();

    public List<Person> getPersons(){
        return db.getPersons();
    }

    public void addPerson(FormEvent e) {
        String name = e.getName();
        String occupation = e.getOccupation();
        int ageCatId = e.getAgeCat();
        String empCatId = e.getEmpCat();
        boolean usCitizen = e.isUsCitizen();
        String taxId = e.getTaxId();
        String gender = e.getGender();

        AgeCategory ageCategory = null;
        switch (ageCatId){
            case 0:
                ageCategory = AgeCategory.child;
                break;
            case 1:
                ageCategory = AgeCategory.adult;
                break;
            case 2:
                ageCategory = AgeCategory.senior;
                break;
//            default:
//                ageCategory = AgeCategory.adult;
        }

        EmploymentCategory employmentCategory;
        if (empCatId.equals("Employed")){
            employmentCategory = EmploymentCategory.employed;
        }
        else if (empCatId.equals("Self-Employed")){
            employmentCategory = EmploymentCategory.selfEmployed;
        }
        else if (empCatId.equals("Unemployed")){
            employmentCategory = EmploymentCategory.unemployed;
        }
        else {
            employmentCategory = EmploymentCategory.other;
            System.err.println(empCatId);
        }

        Gender genderCat;

        if (gender.equals("male")){
            genderCat = Gender.male;
        }
        else {
            genderCat = Gender.female;
        }

        Person person = new Person(name, occupation, ageCategory, employmentCategory, taxId,
                usCitizen, genderCat);


        db.addPerson(person);

    }

    public void saveToFile(File file) throws IOException {
        db.saveToFile(file);
    }

    public void loadFromFile(File file) throws IOException{
        db.loadFromFile(file);
    }

    public void removePerson(int row) {
        db.removePersonAtIndex(row);
    }

    public void save() throws SQLException {
        db.save();
    }

    public void load() throws SQLException {
        db.load();
    }

    public void connect() throws SQLException {
        db.connect();
    }

    public void disconnect() {
        db.disconnect();
    }

}
