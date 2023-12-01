package com.example.noteitapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<Note> allNotes;
    LayoutInflater inflater;
    public NoteAdapter(Activity activity, ArrayList<Note> notes) {
        this.activity = activity;
        this.allNotes = notes;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.allNotes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.allNotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.allNotes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        vi = inflater.inflate(R.layout.note_item, null);

        Note currentNote = this.allNotes.get(position);
        TextView title = vi.findViewById(R.id.title_text_view);
        title.setText(currentNote.getTitle());

        TextView desc = vi.findViewById(R.id.desc_text_view);
        desc.setText(currentNote.getDescription());

        TextView created = vi.findViewById(R.id.date_created_text_view);
        created.setText(new SimpleDateFormat("dd MMM").format(currentNote.getDateCreated()));

        int backgroundColor = (position % 2 == 0) ?
                ContextCompat.getColor(activity.getBaseContext(), R.color.odd) :
                ContextCompat.getColor(activity.getBaseContext(), R.color.even);

        //vi.findViewById(R.id.rel_note).setBackgroundColor((backgroundColor));

        return vi;
    }

    public void clear() {
        this.allNotes.clear();
    }

    public void addAll(List<Note> allNotes) {
        this.allNotes.addAll(allNotes);
    }
}
