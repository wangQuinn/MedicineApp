package com.example.booklibraryapp.calendar.events;

import com.example.booklibraryapp.calendar.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class MedicineEvent extends Event {


    public MedicineEvent(String name, LocalDate date, LocalTime time) {
        super(name, date, time);
    }

    @Override
    public String getEventType() {
        return "MedicineEvent";
    }
}
