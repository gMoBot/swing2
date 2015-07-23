package com.swing2.example.gui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by garrettcoggon on 6/26/15.
 */
public class PersonFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {

        if(f.isDirectory()){
            return true;
        }
        String name = f.getName();

        String extension = Utils.getFileExtension(name);

        if (extension == null){
            return false;
        }
        else if (extension.equals("per")){
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Person Database Files (*.per)";
    }
}
