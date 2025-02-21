package com.example.booklibraryapp.calendar.events;

import com.example.booklibraryapp.R;
import com.example.booklibraryapp.calendar.Event;

import java.time.LocalDate;

public class RefillEvent extends Event {

    private String location;
    private String insurance;

    public RefillEvent(String name, LocalDate date, String time, String location, String insurance) {
        super(name, date, time, insurance);
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

