package com.swing2.example;

import com.swing2.example.gui.MainFrame;

import javax.swing.*;

/**
 * Created by garrettcoggon on 6/25/15.
 */
public class App {
    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();

            }
        });

    }
}
