package com.example.noteitapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ListView notesListView;
    private NoteAdapter noteAdapter;
    private NoteDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        db = new NoteDatabaseHelper(this);
        notesListView = findViewById(R.id.notes_list_view);
        updateUI();

        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            Note note = (Note) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            intent.putExtra("NOTE_ID", note.getId());
            startActivity(intent);
        });
        // findViewById(R.id.fab).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddNoteActivity.class)));

        setBottomNav();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ArrayList<Note> notes = db.getAllNotes();
        if (noteAdapter == null) {
            noteAdapter = new NoteAdapter(this, notes);
            notesListView.setAdapter(noteAdapter);
        } else {
            noteAdapter.clear();
            noteAdapter.addAll(notes);
            noteAdapter.notifyDataSetChanged();
        }
    }

    private void setBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_add) {
                    startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
                }

                if (id == R.id.nav_cal) {
                    startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                }

                if (id == R.id.nav_search) {
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                }

                return true;
            }
        });

    }


}