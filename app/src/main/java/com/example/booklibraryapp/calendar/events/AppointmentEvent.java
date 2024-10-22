package com.example.booklibraryapp.calendar.events;

import com.example.booklibraryapp.R;
import com.example.booklibraryapp.calendar.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentEvent extends Event {

    private String doctor;
    private String location;

    public AppointmentEvent(String name, LocalDate date, LocalTime time, String doctor, String location) {
        super(name, date, time);
        this.doctor = doctor;
        this.location = location;
    }

    @Override
    public String getEventType() {
        return "AppointmentEvent";
    }

   //Overriding
    public static int getEventLayout() {
        return R.layout.layout_appointment_event;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
