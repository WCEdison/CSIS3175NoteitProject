package com.herokuapp.abtik.mobilenotetakingapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public String getDueDateString() {
        return dueDateString;
    }

    public String getLastEditString() {
        return lastEditString;
    }

    public String getTag() {
        return tag;
    }

    public String dueDateString;

    public String lastEditString;

    public String tag;

    public String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
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

    public void setId(int id) {
        this.id = id;
    }
}
