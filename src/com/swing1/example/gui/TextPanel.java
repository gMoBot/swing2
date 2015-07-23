package com.swing1.example.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by garrettcoggon on 6/25/15.
 */
public class TextPanel extends JPanel {
    private JTextArea textArea;

    public TextPanel(){
        textArea = new JTextArea();

        setLayout(new BorderLayout());

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void appendText(String text ){
        textArea.append(text);
    }
}
