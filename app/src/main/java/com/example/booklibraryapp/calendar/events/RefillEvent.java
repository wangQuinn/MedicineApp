package com.example.booklibraryapp.calendar.events;

import com.example.booklibraryapp.R;
import com.example.booklibraryapp.calendar.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class RefillEvent extends Event {

    private String location;
    private String insurance;

    public RefillEvent(String name, LocalDate date, LocalTime time, String location, String insurance) {
        super(name, date, time);
        this.location = location;
        this.insurance = insurance;
    }

    public static int getEventLayout() {
        return R.layout.layout_refill_event;
    }
    @Override
    public String getEventType() {
        return "RefillEvent";
    }
}

