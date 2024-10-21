package com.example.booklibraryapp.calendar;


import static com.example.booklibraryapp.calendar.CalendarUtils.dateFormatter;
import static com.example.booklibraryapp.calendar.CalendarUtils.daysInMonthArray;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booklibraryapp.R;
import com.example.booklibraryapp.WeekViewActivity;

import java.time.LocalDate;
import java.util.ArrayList;

/*
CalendarActivity, to control everything to do with CalendarView.

 */
public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
    }

    private void setMonthView() {
        monthYearText.setText(dateFormatter(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }





    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecylerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    public void previousMonthAction(View view){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();

    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date !=null){
            CalendarUtils.selectedDate = date;
            setMonthView();

        }


    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeekViewActivity.class));
    }
}
