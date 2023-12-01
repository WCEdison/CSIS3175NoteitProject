package com.example.noteitapp;

//package com.herokuapp.abtik.mobilenotetakingapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AddNoteActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private NoteDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        db = new NoteDatabaseHelper(this);
        titleEditText = findViewById(R.id.title_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                Random random = new Random();
                int id = random.nextInt(9000) + 1000;
                db.addNote(new Note(id, title, description));
                Toast.makeText(AddNoteActivity.this, "Note created", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
