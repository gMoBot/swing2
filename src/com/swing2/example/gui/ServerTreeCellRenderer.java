package com.swing2.example.gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

/**
 * Created by garrettcoggon on 7/31/15.
 */
public class ServerTreeCellRenderer implements TreeCellRenderer {

    private JCheckBox leafRenderer;
    private DefaultTreeCellRenderer nonLeafRenderer;
    private Color textForeground;
    private Color textBackground;
    private Color selectionForeground;
    private Color selectionBackground;


    public ServerTreeCellRenderer(){
        leafRenderer = new JCheckBox();
        nonLeafRenderer = new DefaultTreeCellRenderer();

        nonLeafRenderer.setLeafIcon(Utils.createIcon("/com/swing2/example/images/server16.gif"));
        nonLeafRenderer.setOpenIcon(Utils.createIcon("/com/swing2/example/images/webComponent.gif"));
        nonLeafRenderer.setClosedIcon(Utils.createIcon("/com/swing2/example/images/webComponentAdd.gif"));

        textForeground = UIManager.getColor("Tree.textForeground");
        textBackground = UIManager.getColor("Tree.textBackground");
        selectionForeground = UIManager.getColor("Tree.selectionForeground");
        selectionBackground = UIManager.getColor("Tree.selectionBackground");




    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        if (leaf){

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            ServerInfo nodeInfo = (ServerInfo)node.getUserObject();

            if(selected){
                leafRenderer.setForeground(selectionForeground);
                leafRenderer.setBackground(selectionBackground);
            }
            else {
                leafRenderer.setForeground(textForeground);
                leafRenderer.setBackground(textBackground);
            }


            leafRenderer.setText(nodeInfo.toString());
            leafRenderer.setSelected(nodeInfo.isChecked());


            return leafRenderer;
        }
        else return nonLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
    }
}
