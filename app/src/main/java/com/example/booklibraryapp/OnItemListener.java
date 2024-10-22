package com.example.booklibraryapp;
import java.time.LocalDate;

public interface OnItemListener
{
    void onItemClick(int position, LocalDate date);
}
