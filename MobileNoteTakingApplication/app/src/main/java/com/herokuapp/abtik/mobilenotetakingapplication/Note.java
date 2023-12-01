package com.herokuapp.abtik.mobilenotetakingapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

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

//    public Note(String title, String description) {
//        this.title = title;
//        this.description = description;
//    }
    public Note(String title, String description, String dueDateString) {
        this.title = title;
        this.description = description;
        this.dueDateString = dueDateString;
        this.lastEditString = getTodaysDate();
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
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
