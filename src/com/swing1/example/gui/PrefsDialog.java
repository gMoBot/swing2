package com.swing1.example.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by garrettcoggon on 6/28/15.
 */
public class PrefsDialog extends JDialog {
    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel spinnerNumberModel;
    private JTextField userField;
    private JPasswordField passwordField;
    private PrefsListener prefsListener;

    public PrefsDialog(JFrame parent){
        super(parent, "Preferences", false);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        userField = new JTextField(10);
        passwordField = new JPasswordField(10);


        spinnerNumberModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerNumberModel);


        passwordField.setEchoChar('*');


        layoutControls();



        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer port = (Integer) portSpinner.getValue();

                String user = userField.getText();
                char[] password = passwordField.getPassword();

                if (prefsListener != null){
                    prefsListener.preferencesSet(user, new String(password), port);
                }

                setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });





        setSize(300, 200);
        setLocationRelativeTo(parent);

    }

    private void layoutControls() {

        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 15;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");

        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

//        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));


        controlsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        Insets rightPadding = new Insets(0, 0, 0, 15);
        Insets leftPadding = new Insets(0, 0, 0, 0);

        // FIRST ROW //

        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;

        gc.gridx = 0;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("User: "), gc);


        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = leftPadding;

        controlsPanel.add(userField, gc);

        // NEXT ROW //

        gc.gridy++;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;

        controlsPanel.add(new JLabel("Password: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = leftPadding;

        controlsPanel.add(passwordField, gc);


        // NEXT ROW//

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;


        controlsPanel.add(new JLabel("Port: "), gc);


        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = leftPadding;

        controlsPanel.add(portSpinner, gc);

        // Buttons Panel //
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        Dimension btnSize = cancelButton.getPreferredSize();
        okButton.setPreferredSize(btnSize);

        // Add Sub panels to dialog//

        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void setPrefsListener(PrefsListener prefsListener){
        this.prefsListener = prefsListener;
    }

    public void setDefaults(String user, String password, int port){
        userField.setText(user);
        passwordField.setText(password);
        portSpinner.setValue(port);
    }
}
