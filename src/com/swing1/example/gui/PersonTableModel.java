package com.swing1.example.gui;

import com.swing1.example.model.Person;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by garrettcoggon on 6/27/15.
 */
public class PersonTableModel extends AbstractTableModel {

    private List<Person> db;
    private String[] colNames = {"ID", "Name", "Occupation", "AgeCategory", "Employment " +
            "Category", "US Citizen", "Tax Id"};

    public PersonTableModel(){}


    public void setData(List<Person> db){
        this.db = db;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person person = db.get(rowIndex);

        switch (columnIndex){
            case 0:
                return person.getId();
            case 1:
                return person.getName();
            case 2:
                return person.getOccupation();
            case 3:
                return person.getAgeCategory();
            case 4:
                return person.getEmploymentCategory();
            case 5:
                return person.isUsCitizen();
            case 6:
                return person.getTaxId();
            default:
                return null;

        }
    }
}
