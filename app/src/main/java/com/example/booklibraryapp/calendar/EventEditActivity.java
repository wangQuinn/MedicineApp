package com.example.booklibraryapp.calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout; // You need this for eventTypeSpecificLayout
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booklibraryapp.R;
import com.example.booklibraryapp.calendar.events.AppointmentEvent;
import com.example.booklibraryapp.calendar.events.MedicineEvent;
import com.example.booklibraryapp.calendar.events.RefillEvent;

import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private String selectedEventType;
    private FrameLayout eventTypeSpecificLayout;  // The layout for adding specific UI components

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        eventTypeSpecificLayout = findViewById(R.id.eventTypeSpecificLayout); // dynamic layout

        // Spinner (drop-down box) for selecting event types
        Spinner eventTypeSpinner = findViewById(R.id.eventTypeSpinner);

        // Array of event types
        String[] eventTypes = {"MedicineEvent", "RefillEvent", "AppointmentEvent"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, eventTypes);
        eventTypeSpinner.setAdapter(adapter);

        eventTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEventType = eventTypes[position];
                showEventTypeSpecificUI(selectedEventType);  // Call the function to display the correct layout
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Default action if nothing selected
            }
        });
    }

    private void showEventTypeSpecificUI(String eventType) {
        eventTypeSpecificLayout.removeAllViews(); // Clear previous UI

        // Show specific UI based on selected event type
        if ("MedicineEvent".equals(eventType)) {
            // Inflate and add the specific layout for Medicine Event
            View medicineView = getLayoutInflater().inflate(R.layout.layout_medicine_event, null);
            eventTypeSpecificLayout.addView(medicineView);
        } else if ("RefillEvent".equals(eventType)) {
            // Inflate and add the specific layout for Refill Event
            View refillView = getLayoutInflater().inflate(R.layout.layout_refill_event, null);
            eventTypeSpecificLayout.addView(refillView);
        } else if ("AppointmentEvent".equals(eventType)) {
            // Inflate and add the specific layout for Appointment Event
            View appointmentView = getLayoutInflater().inflate(R.layout.layout_appointment_event, null);
            eventTypeSpecificLayout.addView(appointmentView);
        }
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();

        // Depending on the selected event type, instantiate the appropriate event class
        Event newEvent;
        if ("MedicineEvent".equals(selectedEventType)) {
            newEvent = new MedicineEvent(eventName, CalendarUtils.selectedDate, time);
        } else if ("RefillEvent".equals(selectedEventType)) {
            newEvent = new RefillEvent(eventName, CalendarUtils.selectedDate, time);
        } else { // AppointmentEvent
            newEvent = new AppointmentEvent(eventName, CalendarUtils.selectedDate, time);
        }

        Event.eventsList.add(newEvent);  // Add the new event to the event list
        finish();
    }
}
