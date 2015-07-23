package com.swing2.example.gui;

/**
 * Created by garrettcoggon on 6/26/15.
 */
public class Utils {

    public static String getFileExtension(String name){
        int pointIndex = name.lastIndexOf(".");

        if (pointIndex == -1){
            return null;
        }

        if ((pointIndex == name.length() - 1)){
            return null;
        }
        return name.substring(pointIndex+1, name.length());
    }
}
