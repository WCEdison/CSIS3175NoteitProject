package com.example.noteitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private NoteAdapter noteAdapter;

    ListView notesListView;
    private NoteDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        notesListView = findViewById(R.id.cal_list_view);
        db = new NoteDatabaseHelper(this);
        //ArrayList<Note> notes = db.getAllNotes();
        //Toast.makeText(CalendarActivity.this, "Welcome, you have: " + notes.size() + " events in calendar." , Toast.LENGTH_SHORT).show();
        CalendarView calendarView = findViewById(R.id.id_cal);
        TextView txt = findViewById(R.id.txt_noitems);
        txt.setVisibility(View.INVISIBLE);
        ListView listView = findViewById(R.id.cal_list_view);
        listView.setVisibility(View.INVISIBLE);

        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            Note note = (Note) parent.getItemAtPosition(position);
            Intent intent = new Intent(CalendarActivity.this, NoteActivity.class);
            intent.putExtra("NOTE_ID", note.getId());
            startActivity(intent);
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Date date = new Date(year, month, dayOfMonth);
                updateUIWithDate(date);
            }
        });

        setBottomNav();
    }

    private void updateUIWithDate(Date d) {
        ArrayList<Note> notes = db.getAllNotesByDate(d);
        DateFormat dateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dateFormat = new SimpleDateFormat("MM DD YYYY");
        }
        String strDate = dateFormat.format(d);

        if (noteAdapter == null) {
            noteAdapter = new NoteAdapter(this, notes);
            //Toast.makeText(CalendarActivity.this, "No notes found on " + makeDateString(d.getDay(),d.getMonth(),d.getYear()) , Toast.LENGTH_SHORT).show();
            notesListView.setAdapter(noteAdapter);
        } else {
            //Toast.makeText(CalendarActivity.this, "You have: " + notes.size() + " events." , Toast.LENGTH_SHORT).show();
            noteAdapter.clear();
            noteAdapter.addAll(notes);
            noteAdapter.notifyDataSetChanged();
        }

        TextView txt = findViewById(R.id.txt_noitems);
        if (notes.size() <= 0) {
            txt.setVisibility(View.VISIBLE);
        } else {
            txt.setVisibility(View.INVISIBLE);
        }

        notesListView.setVisibility(View.VISIBLE);
        CalendarView calendarView = findViewById(R.id.id_cal);
        calendarView.setVisibility(View.INVISIBLE);
    }

    private void setBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_add) {
                    startActivity(new Intent(CalendarActivity.this, AddNoteActivity.class));
                }

                if (id == R.id.nav_cal) {
                    startActivity(new Intent(CalendarActivity.this, CalendarActivity.class));
                }

                if (id == R.id.nav_search) {
                    startActivity(new Intent(CalendarActivity.this, MainActivity.class));
                }

                return true;
            }
        });
    }


    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

}