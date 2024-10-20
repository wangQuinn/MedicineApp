package com.example.booklibraryapp;
import java.time.LocalDateTime;

/*
Reminder - Abstract class which is inhertiaed from Medicine, Refill, and Appointment Reminders


 */
abstract public class Reminder {
    private String message;
    private LocalDateTime time;
    private String frequency;

    public Reminder(String message, LocalDateTime time, String frequency){
        this.message = message;
        this.time = time;
        this.frequency = frequency;

    }

    abstract void sendNotification();
    abstract void snoozeReminder(int minutes);
    abstract void updateReminder(String newMessage, LocalDateTime newTime, String newFrequency);
    public void cancelReminder(){
        //do stuff
    }



}
