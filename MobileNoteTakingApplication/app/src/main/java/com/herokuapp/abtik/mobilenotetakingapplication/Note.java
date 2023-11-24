package com.herokuapp.abtik.mobilenotetakingapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;

    public Date dueDate;

    public Date lastEdit;

    public String tag;

    public void setTitle(String title) {
        this.title = title;
    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Note() {
    }

    public Note(int id, String title, String description, Date dueDate, Date lastEdit, String tag) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.lastEdit = lastEdit;
        this.tag = tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Date lastEdit) {
        this.lastEdit = lastEdit;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
}
