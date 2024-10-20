package com.example.booklibraryapp;

import java.time.LocalDateTime;

public class AppointmentReminder extends Reminder{


    public AppointmentReminder(String message, LocalDateTime time, String frequency) {
        super(message, time, frequency);

    }

    @Override
    void sendNotification() {
        //dostuff
    }

    @Override
    void snoozeReminder(int minutes) {

    }

    @Override
    void updateReminder(String newMessage, LocalDateTime newTime, String newFrequency) {

    }
}
