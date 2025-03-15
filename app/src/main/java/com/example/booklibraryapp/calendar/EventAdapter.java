package com.example.booklibraryapp.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.booklibraryapp.R;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event>
{
    //this class changes the name on the events that are in the lsitview of weekly View
    public EventAdapter(@NonNull Context context, List<Event> events)
    {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Event event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        View eventIndicator = convertView.findViewById(R.id.eventSidebar);
        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        TextView eventNotesTV  = convertView.findViewById(R.id.eventNoteTV);



        String eventTitle = event.getName() +" "+ event.getTime();
        String notes = "Notes: " + event.getNotes();

        LayerDrawable drawable = (LayerDrawable) eventIndicator.getBackground();
        Drawable shape = drawable.getDrawable(0);
        //ShapeDrawable background = (ShapeDrawable) eventIndicator.getBackground();


        GradientDrawable background = (GradientDrawable) shape;
        if (event.getEventType().equals("MedicineEvent")) {

            background.setColor(ContextCompat.getColor(getContext(), R.color.mutedOrange));
        } else if ((event.getEventType().equals("AppointmentEvent"))) {
            //purple for appointments
           //eventIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mutedPurple));  // Black for appointment
            background.setColor(ContextCompat.getColor(getContext(), R.color.mutedPurple));
        } else if ((event.getEventType().equals("RefillEvent"))) {
            //yellow for refill
            background.setColor(ContextCompat.getColor(getContext(), R.color.mutedYellow));
            //eventIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mutedYellow));  // Light grey for refill
        }

        eventCellTV.setText(eventTitle);
        eventNotesTV.setText(notes); //not working, displays an error.
        return convertView;
    }


}

