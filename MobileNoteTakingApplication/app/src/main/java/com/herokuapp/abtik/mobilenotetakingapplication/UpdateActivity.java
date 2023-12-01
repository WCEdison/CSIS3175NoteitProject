package com.herokuapp.abtik.mobilenotetakingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    EditText title, description;
    Button cancel, save;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Note");
        setContentView(R.layout.activity_update);

        title = findViewById(R.id.editTextTitleUpdate);
        description = findViewById(R.id.editTextDescriptionUpdate);
        cancel = findViewById(R.id.buttonCancelUpdate);
        save = findViewById(R.id.buttonSaveUpdate);

        getData();
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        cancel.setOnClickListener((View v) -> {
            Toast.makeText(getApplicationContext(), "Nothing updated", Toast.LENGTH_LONG).show();
            finish();
        });

        save.setOnClickListener((View v) -> {
            updateNote();
        });
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

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
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

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
    private void updateNote()
    {
        String titleLast = title.getText().toString();
        String descriptionLast = description.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("titleLast", titleLast);
        intent.putExtra("descriptionLast", descriptionLast);
        if (noteId != -1)
        {
            intent.putExtra("noteId", noteId);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void getData()
    {
        Intent i = getIntent();
        noteId = i.getIntExtra("id", -1);
        String noteTitle = i.getStringExtra("title");
        String noteDescription = i.getStringExtra("description");
        String noteDate = i.getStringExtra("dueDateString");
        title.setText(noteTitle);
        description.setText(noteDescription);
    }
}