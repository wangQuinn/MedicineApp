package com.example.booklibraryapp.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

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

        View eventIndicator = convertView.findViewById(R.id.eventIndicator);
        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String eventTitle = event.getName() +" "+ CalendarUtils.formattedTime(event.getTime());

        if (event.getEventType().equals("MedicineEvent")) {
            eventIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkBlue));  // Blue for medicine
        } else if ((event.getEventType().equals("AppointmentEvent"))) {
            eventIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));  // Black for appointment
        } else if ((event.getEventType().equals("RefillEvent"))) {
            eventIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGray));  // Light grey for refill
        }

        eventCellTV.setText(eventTitle);
        return convertView;
    }
}
