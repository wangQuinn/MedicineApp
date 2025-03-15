package com.example.booklibraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "Book_Library.db"; //should probably change from public to private
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "My_Library";
    private static final String Column_ID = "_id";
    private static final String Column_TITLE = "medicine_title";
    private static final String Column_MEALTIME = "mealtime";
    private static final String Column_PILLS = "pills";
    private static final String Column_DOSAGE = "dosage";
    private static final String Column_FREQUENCY = "frequency";
    private static final String Column_REMINDER_TIMES = "reminder_times";
    private static final String Column_STARTDATE= "start_date";
    private static final String Column_ENDDATE = "end_date";
    private static final String Column_USERID = "user_id";//encapsulation!

    private static final String USER_TABLE = "Users";
    private static final String Column_USER = "username";
    private static final String Column_PASSWORD = "password";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Column_TITLE + " TEXT, " +
                        Column_MEALTIME + " TEXT, " +
                        Column_PILLS + " INTEGER, " +
                        Column_DOSAGE + " INTEGER, " +
                        Column_FREQUENCY + " TEXT, " +
                        Column_REMINDER_TIMES + " TEXT, " +
                        Column_STARTDATE + " TEXT, " +
                        Column_ENDDATE + " TEXT, " +



                        Column_USERID + " TEXT, " + // the column user_id will use the username as an individual unique id to find what is needed to display, etc..
                        "FOREIGN KEY" + "(" + Column_USERID + ")" +  "REFERENCES " + USER_TABLE + "(" + Column_USER + "));"; //all SQL commands are written in plain text, so all commands have to be in strings!
        String query2 =
                "CREATE TABLE " + USER_TABLE +
                        " (" + Column_USER + " TEXT PRIMARY KEY , " +
                        Column_PASSWORD + " TEXT); ";

        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);

    }


    //my function
    void addBook(String title, String author, int pages, String userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(Column_TITLE, title);
        cv.put(Column_MEALTIME, author);
        cv.put(Column_PILLS, pages);
        cv.put(Column_USERID, userId);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result ==-1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show(); // double check whatthis does
        }
        else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }



    }

    Cursor readAllData(String userId){ //figure out what cursor object is
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Column_USERID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,  new String[]{userId});

        }

        return cursor;
    }

    void updateData(String row_id, String title, String author, String pages, String dosage, String frequency, String reminder_times, String startDate, String endDate ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_TITLE, title);
        cv.put(Column_MEALTIME, author);
        cv.put(Column_PILLS, pages);
        cv.put(Column_DOSAGE, dosage);
        cv.put(Column_FREQUENCY, frequency);
        cv.put(Column_REMINDER_TIMES, reminder_times);
        cv.put(Column_STARTDATE, startDate);
        cv.put(Column_ENDDATE, endDate);

        long result = db.update(TABLE_NAME, cv, "_id =?", new String[] {row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update. ", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Successfully Updated. ", Toast.LENGTH_LONG).show();
            //Log.d("UpdateActivity", "Updating book with id: " + "_id=?");
            //Log.d("UpdateActivity", "Title: " + title + ", Author: " + author + ", Pages: " + pages);


        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Deletion Failed. ", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(context, "Deletion Success. ", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    //Register stuff
    void addUser(String username, String password){
        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_USER, username);
        cv.put(Column_PASSWORD, password);
        long result = myDb.insert(USER_TABLE, null, cv);
        if(result == -1){
            Toast.makeText(context, "User Creation Failed ", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(context, "User Creation Successful", Toast.LENGTH_SHORT).show();
        }

    }

    boolean checkUserUnique(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + Column_USER + " = ?", new String[]{username});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }

    }
    //Login stuff

    boolean checkUser(String username, String pwd){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + Column_USER + " = ?" + " AND " + Column_PASSWORD + " = ?", new String[]{username, pwd});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }


}
