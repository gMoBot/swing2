package com.swing2.example.gui;

/**
 * Created by garrettcoggon on 8/17/15.
 */
public class Message {

    private String title;
    private String contents;

    public Message(String title, String contents) {
        super();
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
