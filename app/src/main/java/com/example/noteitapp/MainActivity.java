package com.example.noteitapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "NOTEIT";
    private ListView notesListView;
    private NoteAdapter noteAdapter;
    private NoteDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        db = new NoteDatabaseHelper(this);
        notesListView = findViewById(R.id.notes_list_view);
        ArrayList<Note> notes = db.getAllNotes();
        Toast.makeText(MainActivity.this, "Welcome, you have: " + notes.size() + " events.", Toast.LENGTH_SHORT).show();
        TriggerNotification("Note it update!", notes.size() + " events.");

        updateUI();
        createNotificationChannel();
        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            Note note = (Note) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            intent.putExtra("NOTE_ID", note.getId());
            startActivity(intent);
        });
        // findViewById(R.id.fab).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddNoteActivity.class)));

        setBottomNav();
    }

//    void TriggerNotification(){
//        Intent notificationintent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//        notificationintent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
//        notificationintent.putExtra(Settings.EXTRA_CHANNEL_ID, CHANNEL_ID);
//        startActivity(notificationintent);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ArrayList<Note> notes = db.getAllNotes();
        // Toast.makeText(MainActivity.this, "Welcome, you have: " + notes.size() + " events." , Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }

                return true;
            }
        });

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    void TriggerNotification(String title, String content) {
        // Create an explicit intent for an Activity in your app.
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that fires when the user taps the notification.
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        int id = (int) Calendar.getInstance().getTime().getTime();
        notificationManager.notify(id, builder.build());
    }
}