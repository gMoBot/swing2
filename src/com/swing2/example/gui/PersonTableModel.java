package com.swing2.example.gui;

import com.swing2.example.model.EmploymentCategory;
import com.swing2.example.model.Person;

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
    public Class<?> getColumnClass(int column) {

        switch (column){
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return EmploymentCategory.class;
            case 5:
                return Boolean.class;
            case 6:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 1:
                return true;
            case 4:
                return true;
            case 5:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (db == null) return;

        Person person = db.get(row);

        switch (column){
            case 1:
                person.setName((String)aValue);
                break;
            case 4:
                person.setEmploymentCategory((EmploymentCategory)aValue);
                break;
            case 5:
                person.setUsCitizen((Boolean) aValue);
                break;
            default:
                return;
        }
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
