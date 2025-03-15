package com.example.booklibraryapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.Manifest;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.*;
import android.widget.*; // adds all widgets

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {


    //adding objects from UI
    EditText title_input, time_input, pills_input;
    Button add_button, picture_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        title_input = findViewById(R.id.title_input);
        time_input = findViewById(R.id.unit_input);
        pills_input = findViewById(R.id.pills_input);
        add_button = findViewById(R.id.add_button);
        picture_button = findViewById(R.id.picture_button);
        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.unitAddspinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"pill", "piece", "ml", "mg",};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);
        dropdown.setSelection(0, false);

        Spinner dose = findViewById(R.id.doseAddspinner);
        String[] doseitems = new String[]{"mg", "ml", "g"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dose.setAdapter(adapter2);
        dose.setSelection(0, false);

        String userId = getIntent().getStringExtra("USER_ID");
        Log.d("AddActivity", "Received USER_ID: " + userId);

        createNotificationChannel();

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "medication_reminder_channel")
//                .setSmallIcon(R.drawable.baseline_notification_important_24) // Icon for the notification
//                .setContentTitle("Test Notification")
//                .setContentText("This is a test notification.")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
//        notificationManager.notify(1, builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.mutedRed));
        }


        picture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 100);
                }

            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override //abstract classes waho!!
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addBook(
                        title_input.getText().toString().trim(),
                        time_input.getText().toString().trim(),
                        Integer.parseInt(pills_input.getText().toString().trim()),
                        userId);
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        Log.d("DEBUG", "Current title: " + getSupportActionBar().getTitle());


    }

    public void addpopTimePicker(View view) {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog instance
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Handle the selected time (e.g., update a TextView, etc.)
                        // For example, show a toast with the selected time
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        Toast.makeText(getApplicationContext(), "Selected time: " + time, Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("selectedHour", hourOfDay);
                        editor.putInt("selectedMinute", minute);
                        editor.apply();
                        setDailyAlarm(hourOfDay, minute);
                    }
                },
                hour, // initial hour
                minute, // initial minute
                true // 24-hour format
        );

        // Show the time picker dialog
        timePickerDialog.show();
    }

    public void setDailyAlarm(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0); // Optional: Set to 0 seconds for precision


        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        Log.d("Alarm", "Setting alarm for " + hourOfDay + ":" + minute + " on " + calendar.getTime());


        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        1);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "medication_reminder_channel";
            CharSequence channelName = "Medication Reminders";
            String channelDescription = "Channel for medication reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;


            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can show notifications now
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied. Cannot show notifications.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

