package com.example.booklibraryapp.calendar.events;

import com.example.booklibraryapp.calendar.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class RefillEvent extends Event {


    public RefillEvent(String name, LocalDate date, LocalTime time) {
        super(name, date, time);
    }

    @Override
    public String getEventType() {
        return "RefillEvent";
    }
}

