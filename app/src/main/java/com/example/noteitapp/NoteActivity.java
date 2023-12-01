package com.example.noteitapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Date dueDate;
    private Date originalDate;
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
            originalDate = note.getDateCreated();
            titleTextView.setText(note.getTitle());
            descriptionTextView.setText(note.getDescription());
        }
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
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
                try {
                    newNote.setDateCreated(dueDate);
                } catch (Exception error1) {

                }
                db.updateNote(newNote);
                Toast.makeText(NoteActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dueDate = new Date(year, month, day);
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                Toast.makeText(NoteActivity.this, "Date Selected:" + date, Toast.LENGTH_SHORT).show();
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

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

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

}
