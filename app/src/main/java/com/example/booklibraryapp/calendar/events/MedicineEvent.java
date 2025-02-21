package com.example.booklibraryapp.calendar.events;

import com.example.booklibraryapp.R;
import com.example.booklibraryapp.calendar.Event;

import java.time.LocalDate;

public class MedicineEvent extends Event {

    private String frequency;
    private String notes;

    public MedicineEvent(String name, LocalDate date, String time, String frequency, String notes) {
        super(name, date, time, notes);
        this.frequency = frequency;
        this.notes = notes;
    }

    public static int getEventLayout() {
        return R.layout.layout_medicine_event;
    }

    @Override
    public String getEventType() {
        return "MedicineEvent";
    }
}
