package com.example.booklibraryapp.calendar.events;

import com.example.booklibraryapp.calendar.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentEvent extends Event {


    private String location;

    public AppointmentEvent(String name, LocalDate date, LocalTime time, String location) {
        super(name, date, time);
        this.location = location;
    }

    @Override
    public String getEventType() {
        return "AppointmentEvent";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
