package com.swing2.example.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by garrettcoggon on 8/18/15.
 */
public class MessageListRenderer implements ListCellRenderer{

    private JPanel panel;
    private JLabel label;
    private Color selectedColor;
    private Color normalColor;


    public MessageListRenderer(){
        panel = new JPanel();
        label = new JLabel();

        selectedColor = new Color(210, 210, 255);
        normalColor = Color.white;

        label.setIcon(Utils.createIcon("/com/swing2/example/images/info24.gif"));

        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.add(label);


    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {


        Message message = (Message)value;
        label.setText(message.getTitle());


        panel.setBackground(cellHasFocus ? selectedColor : normalColor);
        label.setOpaque(true);

        return panel;
    }
}
