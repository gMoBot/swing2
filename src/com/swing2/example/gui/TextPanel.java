package com.swing2.example.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by garrettcoggon on 6/25/15.
 */
public class TextPanel extends JPanel {
    private JTextArea textArea;

    public TextPanel(){
        textArea = new JTextArea();

        textArea.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        textArea.setFont(new Font("Serif", Font.PLAIN, 20));

        setLayout(new BorderLayout());

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void appendText(String text ){
        textArea.append(text);
    }


    public void setText(String text){
        textArea.setText(text);
    }
}

