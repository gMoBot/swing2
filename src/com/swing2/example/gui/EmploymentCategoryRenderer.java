package com.swing2.example.gui;

import com.swing2.example.model.EmploymentCategory;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by garrettcoggon on 8/18/15.
 */
public class EmploymentCategoryRenderer implements TableCellRenderer {

    private JComboBox comboBox;

    public EmploymentCategoryRenderer(){
        comboBox = new JComboBox(EmploymentCategory.values());
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {


        comboBox.setSelectedItem(value);
        return comboBox;
    }
}
