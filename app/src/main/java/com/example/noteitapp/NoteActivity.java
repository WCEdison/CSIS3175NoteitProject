package com.example.noteitapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class NoteActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView descriptionTextView;
    private NoteDatabaseHelper db;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        db = new NoteDatabaseHelper(this);
        titleTextView = findViewById(R.id.title_text_view);
        descriptionTextView = findViewById(R.id.description_text_view);

        int noteId = getIntent().getIntExtra("NOTE_ID", -1);
        if (noteId != -1) {
            note = db.getNote(noteId);
            titleTextView.setText(note.getTitle());
            descriptionTextView.setText(note.getDescription());
        }

        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNote(note.getId());
                Toast.makeText(NoteActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText titleEditText = findViewById(R.id.title_text_view);
                EditText descriptionEditText = findViewById(R.id.description_text_view);
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                Note newNote = new Note(note.getId(), title, description);
                db.updateNote(newNote);
                Toast.makeText(NoteActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
