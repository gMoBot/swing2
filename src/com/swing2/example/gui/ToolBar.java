package com.swing2.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Created by garrettcoggon on 6/25/15.
 */
public class ToolBar extends JToolBar implements ActionListener{
    private JButton saveButton;
    private JButton refreshButton;
    private ToolbarListener toolbarListener;


    public ToolBar(){
        setBorder(BorderFactory.createEtchedBorder());
        setFloatable(false);

        saveButton = new JButton();
        saveButton.setIcon(Utils.createIcon("/com/swing2/example/images/save.gif"));
        saveButton.setToolTipText("Save");

        refreshButton = new JButton();
        refreshButton.setIcon(Utils.createIcon("/com/swing2/example/images/refresh.gif"));

        saveButton.addActionListener(this);
        refreshButton.addActionListener(this);
        refreshButton.setToolTipText("Refresh");

        add(saveButton);
        add(refreshButton);

    }


    public void setToolbarListener(ToolbarListener listener) {
    this.toolbarListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == saveButton) {
            if (toolbarListener != null){
                toolbarListener.saveEventOccured();
            }
        }
            else if (clicked == refreshButton) {
            if (toolbarListener != null) {
                toolbarListener.refreshEventOccured();
            }
        }
    }
}
