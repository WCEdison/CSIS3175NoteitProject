package com.example.noteitapp;

import java.util.Date;

public class Note {
    private int id;
    private String title;
    private String description;
    private Date dateCreated;

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Note(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateCreated = new Date();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
