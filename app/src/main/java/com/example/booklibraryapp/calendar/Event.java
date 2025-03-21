package com.example.booklibraryapp.calendar;

import com.example.booklibraryapp.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public abstract class Event {

    public static ArrayList<Event> eventsList = new ArrayList<>();

    //initialize variables
    private String name;
    private LocalDate date;
    private String time;
    private String notes;

    //constructor
    public Event(String name, LocalDate date, String time, String notes) {
        this.date = date;
        this.name = name;
        this.time = time;
        this.notes = notes;
    }


    //abstract classes
    public abstract String getEventType();

    //public static classes that can be overloaded.
    public static ArrayList<Event> eventsForDate(LocalDate date){
        ArrayList<Event> events = new ArrayList<>();

        for(Event event: eventsList){
            if(event.getDate().equals(date))
                events.add(event);
        }
        return events;
    }
    public static int getEventLayout(){
        return R.layout.layout_medicine_event; //default layout
    }


    //setters and getters
    //Date
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    //Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Time
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getNotes(){ return notes; }
    public void setNotes(String notes) {
        this.notes = notes;}


}
