package com.swing2.example.gui;

import com.swing2.example.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

/**
 * Created by garrettcoggon on 6/25/15.
 */
public class MainFrame extends JFrame {

    //TODO: intercept window closing to disconnect from db

    private TextPanel textPanel;
    private ToolBar toolBar;
    private FormPanel formPanel;
    private JFileChooser fileChooser;
    private Controller controller;
    private TablePanel tablePanel;
    private PrefsDialog prefsDialog;
    private Preferences prefs;

    public MainFrame(){
        super("Personnel App");

        setLayout(new BorderLayout());

        textPanel = new TextPanel();
        toolBar = new ToolBar();
        formPanel = new FormPanel();
        tablePanel = new TablePanel();
        prefsDialog = new PrefsDialog(this);

        prefs = Preferences.userRoot().node("db");


        controller = new Controller();

        tablePanel.setData(controller.getPersons());


        tablePanel.setPersonTableListener(new PersonTableListener() {
            public void rowDeleted(int row) {
                System.out.println(row);
                controller.removePerson(row);

            }
        });

        prefsDialog.setPrefsListener(new PrefsListener() {
            @Override
            public void preferencesSet(String user, String password, int port) {
                System.out.println(user + ":" + password);

                prefs.put("user", user);
                prefs.put("password", password);
                prefs.putInt("port", port);

            }
        });


        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        int port = prefs.getInt("port", 3306);
        prefsDialog.setDefaults(user, password, port);


        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        setJMenuBar(createMenuBar());


        toolBar.setToolbarListener(new ToolbarListener() {
            @Override
            public void saveEventOccured() {
                System.out.println("save");

                connect();

                try {
                    controller.save();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database",
                            "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void refreshEventOccured() {

                connect();

                try {
                    controller.load();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from database",
                            "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
                }

                tablePanel.refresh();
                System.out.println("refresh");

            }
        });

        formPanel.setFormListener(new FormListener() {
            public void formEventOccured(FormEvent e) {
//                String name = e.getName();
//                String occupation = e.getOccupation();
//                int ageCat = e.getAgeCat();
//                String empCat = e.getEmpCat();
//                String taxId = e.getTaxId();
//                boolean usCitizen = e.isUsCitizen();
//                String gender = e.getGender();
//
//                textPanel.appendText(name + ":" + occupation + ageCat + empCat + taxId +
//                        usCitizen + gender + "\n");
                controller.addPerson(e);
                tablePanel.refresh();

                // TODO: Clear form on submit
                //set values to null and refresh

            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("System Closing...");
                controller.disconnect();
                dispose();
                System.gc();

            }
        });

//        add(textPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
        add(toolBar, BorderLayout.NORTH);
        add(formPanel, BorderLayout.WEST);


        setMinimumSize(new Dimension(500, 400));
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu windowMenu = new JMenu("Window");

        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data..");
        JMenuItem exitItem = new JMenuItem("Exit");

        JMenu showMenu = new JMenu("Show");

        JMenuItem prefsItem = new JMenuItem("Preferences...");

        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);


        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        windowMenu.add(prefsItem);


        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);



        menuBar.add(fileMenu);
        menuBar.add(windowMenu);


        prefsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prefsDialog.setVisible(true);
            }
        });



        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();

                formPanel.setVisible(menuItem.isSelected());

            }
        });

        // on this mac-must use ctrl + option/alt + indicated key //
        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_Q);


        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));


        // ActionEvent.CTRL_MASK //
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.META_MASK));

        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));


        importDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.loadFromFile(fileChooser.getSelectedFile());
                        tablePanel.refresh();
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from " +
                                "File", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        exportDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.saveToFile(fileChooser.getSelectedFile());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to" +
                                "File", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });


        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String userName = JOptionPane.showInputDialog(MainFrame
//                                .this,
//                        "Enter your username", "Enter Username",
//                        JOptionPane.OK_OPTION|JOptionPane.QUESTION_MESSAGE);


                int action = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Do you really want to exit the application", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

                if (action == JOptionPane.OK_OPTION) {
                    WindowListener[] listeners = getWindowListeners();
                    for(WindowListener listener : listeners){
                        listener.windowClosing(new WindowEvent(MainFrame.this, 0));
                    }
                }
            }
        });


        return menuBar;
    }

    private void connect(){
        try {
            controller.connect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database",
                    "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
        }
    }
}
