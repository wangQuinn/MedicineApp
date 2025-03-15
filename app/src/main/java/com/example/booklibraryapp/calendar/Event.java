package com.example.booklibraryapp.calendar;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Event {


    public Event next; // Reference to next event
    public Event prev; // Reference to previous event

    private String name;
    private LocalDate date;
    private String time;
    private String notes;

    // Constructor
    public Event(String name, LocalDate date, String time, String notes) {
        this.date = date;
        this.name = name;
        this.time = time;
        this.notes = notes;
        this.next = null;
        this.prev = null;
    }

    public static ArrayList<Event> eventsList = new ArrayList<>();

    // Abstract method for event type
    public abstract String getEventType();

    // Static method to get events for a specific date
    public static ArrayList<Event> eventsForDate(LocalDate date){
        ArrayList<Event> events = new ArrayList<>();
        for(Event event: eventsList){
            if(event.getDate().equals(date))
                events.add(event);
        }
        return events;
    }


    // Setters and getters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
