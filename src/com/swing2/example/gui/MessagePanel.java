package com.swing2.example.gui;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

/**
 * Created by garrettcoggon on 7/31/15.
 */
public class MessagePanel extends JPanel {

    private JTree serverTree;
    private DefaultTreeCellRenderer treeCellRenderer;

    public MessagePanel(){
        treeCellRenderer = new DefaultTreeCellRenderer();

        treeCellRenderer.setLeafIcon(Utils.createIcon("/com/swing2/example/images/server16.gif"));
        treeCellRenderer.setOpenIcon(Utils.createIcon("/com/swing2/example/images/webComponent.gif"));
        treeCellRenderer.setClosedIcon(Utils.createIcon("/com/swing2/example/images/webComponentAdd.gif"));

        serverTree = new JTree(createTree());
        serverTree.setCellRenderer(treeCellRenderer);
        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        serverTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
                Object userobject = node.getUserObject();

                if (userobject instanceof ServerInfo ){
                    int id = ((ServerInfo) userobject).getId();
                    System.out.println("User id: " + id);

                }

                System.out.println(userobject);
            }
        });

        setLayout(new BorderLayout());


        add(new JScrollPane(serverTree), BorderLayout.CENTER);
    }

    private DefaultMutableTreeNode createTree(){

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");

        DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo("New York", 0 ));
        DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo("Boston", 1));
        DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfo("Los Angeles",
                2));

        branch1.add(server1);
        branch1.add(server2);
        branch1.add(server3);

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("London", 3));
        DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("Edinburgh", 4));

        branch2.add(server4);
        branch2.add(server5);

        top.add(branch1);
        top.add(branch2);

        return top;


    }

}

class ServerInfo {
    private String name;
    private int id;

    public ServerInfo(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String toString(){
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}