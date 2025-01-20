package com.example.booklibraryapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.*;

/*
MainActivity, the main screen a user sees after login.

 */

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "my_channel";
    private static final String NOTIFICATION_ID = "1";

    private NotificationManagerCompat notificationManager;

    RecyclerView recyclerView;
    FloatingActionButton add_button, calendar_button;

    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages; //for SQL database
    CustomAdapter customAdapter;

    ImageView empty_imageView;
    TextView no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //my stuff

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        calendar_button = findViewById(R.id.calendar_button);
        empty_imageView = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.no_data);
        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannel();
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userId = getLoggedInUserId();
                showNotification(view);
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);



            }

        }) ;

        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this, WeekViewActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title= new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(MainActivity.this,this, book_id, book_title, book_author, book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            recreate(); // tells activity to refresh
        }
    }

    void storeDataInArrays(){

        String userId = getLoggedInUserId();
        Cursor cursor = myDB.readAllData(userId);
        if(cursor.getCount() == 0){ // means there is no data
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
            no_data.setVisibility(View.VISIBLE);
            empty_imageView.setVisibility(View.VISIBLE);
        } else{
            no_data.setVisibility(View.GONE);
            empty_imageView.setVisibility(View.GONE);
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }

            no_data.setVisibility(View.GONE);
            empty_imageView.setVisibility(View.GONE);
        }

        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
            confirmDialog();

        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure you want to delete all medications?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }


        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }

    public String getLoggedInUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        return sharedPreferences.getString("USER_ID", null);
    }

    private void showNotification(View view) {
        Intent intent = new Intent(this, MainActivity.class); // intent message of an operation to be perform. aka launch activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE); // setGlags preserves the user's expected navigation experience after they open your app using the notification.

        NotificationCompat.Builder builder = null;



        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("It's time to take your medication.")
                .setContentText("Take your medication now.")
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setFullScreenIntent(pendingIntent, true)
                .setSmallIcon(R.drawable.baseline_notification_important_24)
                .setAutoCancel(true);



        Notification notification;
        notification = builder.build();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        }
        else{
            notificationManager.notify(Integer.parseInt(NOTIFICATION_ID), notification);
        }





    }

    //check if the version is greater than 29 and
    private void createNotificationChannel(){

        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        // Delete existing channel if it exists
        if (notificationManager.getNotificationChannel(CHANNEL_ID) != null) {
            notificationManager.deleteNotificationChannel(CHANNEL_ID);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            CharSequence channelName = "My Channel";
            String channelDescription = "My Channel Description";

            int importance = NotificationManager.IMPORTANCE_HIGH; // = 4 (because it needs to be heads-up)
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);

            notificationManager.createNotificationChannel(channel);

        }

    }

}
