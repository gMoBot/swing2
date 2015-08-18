package com.swing2.example.gui;

import com.swing2.example.controller.MessageServer;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by garrettcoggon on 7/31/15.
 */
public class MessagePanel extends JPanel implements ProgressDialogListener {

    private ProgressDialog progressDialog;

    private JTree serverTree;
    private ServerTreeCellRenderer treeCellRenderer;
    private ServerTreeCellEditor treeCellEditor;

    private Set<Integer> selectedServers;
    private MessageServer messageServer;
    private SwingWorker<java.util.List<Message>, Integer> worker;

    public MessagePanel(JFrame parent){

        progressDialog = new ProgressDialog(parent, "Messages Downloading...");
        progressDialog.setProgressDialogListener(this);

        messageServer = new MessageServer();

        selectedServers = new TreeSet<Integer>();
        selectedServers.add(0);
        selectedServers.add(1);
        selectedServers.add(4);



        treeCellRenderer = new ServerTreeCellRenderer();
        treeCellEditor = new ServerTreeCellEditor();

        serverTree = new JTree(createTree());
        serverTree.setCellRenderer(treeCellRenderer);
        serverTree.setCellEditor(treeCellEditor);
        serverTree.setEditable(true);

        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        treeCellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                ServerInfo info = (ServerInfo) treeCellEditor.getCellEditorValue();

                int serverId = info.getId();

                if (info.isChecked()){
                    selectedServers.add(serverId);
                }
                else {
                    selectedServers.remove(serverId);
                }

                messageServer.setSelectedServers(selectedServers);

                System.out.println(info + " : " + info.getId() + " : " + info.isChecked());


                retrieveMessages();

            }

            @Override
            public void editingCanceled(ChangeEvent e) {
            }
        });
//        serverTree.addTreeSelectionListener(new TreeSelectionListener() {
//            @Override
//            public void valueChanged(TreeSelectionEvent e) {
//                DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
//                Object userobject = node.getUserObject();
//
//                if (userobject instanceof ServerInfo ){
//                    int id = ((ServerInfo) userobject).getId();
//                    System.out.println("User id: " + id);
//
//                }
//
//                System.out.println(userobject);
//            }
//        });

        setLayout(new BorderLayout());


        add(new JScrollPane(serverTree), BorderLayout.CENTER);
    }

    private void retrieveMessages(){

        progressDialog.setMaximum(messageServer.getMessageCount());

        progressDialog.setVisible(true);


        worker = new SwingWorker<java.util
                .List<Message>, Integer>() {
            @Override
            protected List<Message> doInBackground() throws Exception {

                List<Message> retrievedMessages = new ArrayList<Message>();

                int count = 0;

                for (Message message: messageServer) {
                    if(isCancelled()){
                        break;
                    }
                    System.out.println(message.getTitle());
                    retrievedMessages.add(message);

                    count++;
                    publish(count);
                }
                return retrievedMessages;
            }

            @Override
            protected void done() {
                progressDialog.setVisible(false);

                if(isCancelled()){return;}

                try {
                    List<Message> retrievedMessages = get();
                    System.out.println("Retrieved " + retrievedMessages.size() + " messages");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void process(List<Integer> counts) {
                int retrieved = counts.get(counts.size() - 1);

                progressDialog.setValue(retrieved);

//                System.out.println("Got " + retrieved + " messages");

            }

        };

        worker.execute();


    }
    private DefaultMutableTreeNode createTree(){

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");

        DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo("New York", 0,
                selectedServers.contains(0)));
        DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo("Boston", 1,
                selectedServers.contains(1)));
        DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfo("Los Angeles",
                2, selectedServers.contains(2)));

        branch1.add(server1);
        branch1.add(server2);
        branch1.add(server3);

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("London", 3,
                selectedServers.contains(3)));
        DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("Edinburgh",
                4, selectedServers.contains(4)));

        branch2.add(server4);
        branch2.add(server5);

        top.add(branch1);
        top.add(branch2);

        return top;


    }

    @Override
    public void progressDialogCancelled() {

        System.out.println("Cancelled");

        if(worker != null){
            worker.cancel(true);
        }

    }
}

