package com.swing1.example;

import com.swing1.example.model.*;

import java.sql.SQLException;

/**
 * Created by garrettcoggon on 6/28/15.
 */
public class TestDatabase {

    public static void main(String[] args){
        System.out.println("Running Database");

        Database db = new Database();

        try {
            db.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.addPerson(new Person("Joe", "liontamer", AgeCategory.adult,
                EmploymentCategory
                .employed, "777", true, Gender.male));

        db.addPerson(new Person("Susan", "artist", AgeCategory.senior,
                EmploymentCategory
                        .selfEmployed, " ", false, Gender.female));

        try {
            db.save();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            db.load();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.disconnect();

    }
}
