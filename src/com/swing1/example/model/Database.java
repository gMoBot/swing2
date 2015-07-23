package com.swing1.example.model;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Created by garrettcoggon on 6/26/15.
 */
public class Database {

    private List<Person> persons;
    private Connection con;

    public Database(){
        persons = new LinkedList<Person>();
    }

    public void addPerson(Person person){
        persons.add(person);
    }

    public List<Person> getPersons(){
        return Collections.unmodifiableList(persons);
    }

    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        Person[] personArray = persons.toArray(new Person[persons.size()]);

        oos.writeObject(personArray);

        oos.close();

    }



    public void loadFromFile(File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            Person[] personArray = (Person[]) ois.readObject();

            persons.clear();
            persons.addAll(Arrays.asList(personArray));


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ois.close();
    }

    public void removePersonAtIndex(int row) {
        persons.remove(row);
    }

    public void connect() throws SQLException {
        if (con != null){
            return;
        }
//        try {
//            Class.forName("org.sqlite.JDBC");
            String connectionUrl = "jdbc:sqlite:swing.db";
            // TODO: set up user with restricted priveleges, name = 2nd arg, password = 3rd//
            con = DriverManager.getConnection(connectionUrl);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException("Unable to find class for loading database", e);
//        }
        if (con != null) {

            System.out.println("Connected to db" + con);
        }
    }

    public void disconnect(){

        if (con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Cannot close database");
            }
        }
    }

    public void load() throws  SQLException{
        persons.clear();

        String sql = "select person_id, name, age_cat, occupation, emp_cat, tax_id, isus_citizen, gender from persons order by name";

        Statement selectStatement = con.createStatement();

        ResultSet resultSet = selectStatement.executeQuery(sql);

        while (resultSet.next()){
            int id = resultSet.getInt("person_id");
            String name = resultSet.getString("name");
            String age = resultSet.getString("age_cat");
            String occupation = resultSet.getString("occupation");
            String empCat = resultSet.getString("emp_cat");
            String taxId = resultSet.getString("tax_id");
            Boolean isUs = resultSet.getBoolean("isus_citizen");
            String gender = resultSet.getString("gender");

            Person person = new Person(id, name, occupation, AgeCategory.valueOf(age),
                    EmploymentCategory.valueOf(empCat), taxId, isUs, Gender.valueOf(gender));
            persons.add(person);


//            System.out.println(person);
        }

        resultSet.close();
        selectStatement.close();

    }


    public void save() throws SQLException {
        String checkSQL = "select count(*) as count from persons where person_id=?";

        PreparedStatement checkStmnt = con.prepareStatement(checkSQL);

        String insertSQL = "insert into persons (person_id, name, occupation, age_cat, emp_cat, " +
                "tax_id, isus_citizen, gender) values (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement insertStmnt = con.prepareStatement(insertSQL);

        String updateSQL = "update persons set name=?, occupation=?, age_cat=?, emp_cat=?," +
                "tax_id=?, isus_citizen=?, gender=? where person_id=?";
        PreparedStatement updateStmnt = con.prepareStatement(updateSQL);

        for (Person person : persons){
            int id = person.getId();

            String name = person.getName();
            String occupation = person.getOccupation();
            AgeCategory age = person.getAgeCategory();
            EmploymentCategory empCat = person.getEmploymentCategory();
            String taxId = person.getTaxId();
            boolean isUsCitizen =  person.isUsCitizen();
            Gender gender = person.getGender();


            int paramindex = 1;

            checkStmnt.setInt(paramindex, id);

            ResultSet checkresult = checkStmnt.executeQuery();
            checkresult.next();

            int count = checkresult.getInt(1);

            if (count == 0){
                System.out.println("Inserting person with id: " + id);

                int col = 1;
                insertStmnt.setInt(col++, id);
                insertStmnt.setString(col++, name);
                insertStmnt.setString(col++, occupation);
                insertStmnt.setString(col++, age.name());
                insertStmnt.setString(col++, empCat.name());
                insertStmnt.setString(col++, taxId);
                insertStmnt.setBoolean(col++, isUsCitizen);
                insertStmnt.setString(col++, gender.name());

                insertStmnt.executeUpdate();

            }
            else {
                System.out.println("Updating person with id: " + id);


                int col = 1;
                updateStmnt.setString(col++, name);
                updateStmnt.setString(col++, occupation);
                updateStmnt.setString(col++, age.name());
                updateStmnt.setString(col++, empCat.name());
                updateStmnt.setString(col++, taxId);
                updateStmnt.setBoolean(col++, isUsCitizen);
                updateStmnt.setString(col++, gender.name());
                updateStmnt.setInt(col++, id);


                updateStmnt.executeUpdate();

            }

            System.out.println("Count for person with ID: " + id + "is" + count);

        }
        updateStmnt.close();
        insertStmnt.close();
        checkStmnt.close();
    }

}
