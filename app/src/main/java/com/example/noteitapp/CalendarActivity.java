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

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        if (noteAdapter == null) {
            noteAdapter = new NoteAdapter(this, notes);
            notesListView.setAdapter(noteAdapter);
        } else {
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

//                if (id == R.id.nav_search) {
//                    startActivity(new Intent(CalendarActivity.this, SearchActivity.class));
//                }

                return true;
            }
        });
    }
}