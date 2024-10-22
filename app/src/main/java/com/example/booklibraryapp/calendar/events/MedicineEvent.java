package com.example.booklibraryapp.calendar.events;

import com.example.booklibraryapp.R;
import com.example.booklibraryapp.calendar.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class MedicineEvent extends Event {


    private String frequency;
    private String notes;

    public MedicineEvent(String name, LocalDate date, LocalTime time, String frequency, String notes) {
        super(name, date, time);
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
