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
    private static final String DATABASE_NAME = "Book_Library.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "My_Library";
    private static final String Column_ID = "_id";
    private static final String Column_TITLE = "name";
    private static final String Column_DOSAGE = "dosage";
    private static final String Column_FREQUENCY = "frequency";
    private static final String Column_STARTDATE = "start_date";
    private static final String Column_ENDDATE = "end_date";
    private static final String Column_USERID = "user_id";
    private static final String Column_TIMEOF_DAY = "time_of_day";
    private static final String Column_NOTES = "notes";
    private static final String Column_REMINDER_ENABLED = "reminder_enabled";
    private static final String Column_CREATED_AT = "created_at";
    private static final String Column_UPDATED_AT = "updated_at";
    private static final String Column_SALT = "salt";


    private static final String USER_TABLE = "Users";
    private static final String Column_USER = "username";
    private static final String Column_PASSWORD = "password";

    // Tags
    private static final String TAG_TABLE = "Tags";
    private static final String Column_TAG_ID = "id";
    private static final String Column_TAG_NAME = "name";

    //medication tag associations
    private static final String MEDICATION_TAG_ASSOCIATION_TABLE = "Medication_Tag_Association";
    private static final String Column_MEDICATION_ID = "medication_id";
    private static final String Column_TAG_ID_ASSOCIATION = "tag_id";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Column_TITLE + " TEXT NOT NULL, " +
                        Column_DOSAGE + " TEXT, " +
                        Column_FREQUENCY + " TEXT NOT NULL, " +
                        Column_STARTDATE + " DATE NOT NULL, " +
                        Column_ENDDATE + " DATE, " +
                        Column_TIMEOF_DAY + " TEXT, " +
                        Column_NOTES + " TEXT, " +
                        Column_REMINDER_ENABLED + " BOOLEAN DEFAULT TRUE, " +
                        Column_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        Column_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        Column_USERID + " INTEGER NOT NULL, " +
                        "FOREIGN KEY (" + Column_USERID + ") REFERENCES " + USER_TABLE + "(" + Column_USER + "));";

        String query2 =
                "CREATE TABLE " + USER_TABLE +
                        " (" + Column_USER + " TEXT PRIMARY KEY, " +
                        Column_SALT +
                        Column_PASSWORD + " TEXT);";

        String query3 =
                "CREATE TABLE " + TAG_TABLE +
                        " (" + Column_TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Column_TAG_NAME + " VARCHAR UNIQUE NOT NULL);";


        String query4 =
                "CREATE TABLE " + MEDICATION_TAG_ASSOCIATION_TABLE +
                        " (" + Column_MEDICATION_ID + " INTEGER NOT NULL, " +
                        Column_TAG_ID_ASSOCIATION + " INTEGER NOT NULL, " +
                        "PRIMARY KEY (" + Column_MEDICATION_ID + ", " + Column_TAG_ID_ASSOCIATION + "), " +
                        "FOREIGN KEY (" + Column_MEDICATION_ID + ") REFERENCES " + TABLE_NAME + "(" + Column_ID + "), " +
                        "FOREIGN KEY (" + Column_TAG_ID_ASSOCIATION + ") REFERENCES " + TAG_TABLE + "(" + Column_TAG_ID + "));";

        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }


    void addMedication(String name, String dosage, String frequency, String startDate, String endDate, String timeOfDay, String notes, boolean reminderEnabled, String userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_TITLE, name);
        cv.put(Column_DOSAGE, dosage);
        cv.put(Column_FREQUENCY, frequency);
        cv.put(Column_STARTDATE, startDate);
        cv.put(Column_ENDDATE, endDate);
        cv.put(Column_TIMEOF_DAY, timeOfDay);
        cv.put(Column_NOTES, notes);
        cv.put(Column_REMINDER_ENABLED, reminderEnabled ? 1 : 0); // Store true as 1 and false as 0
        cv.put(Column_USERID, userId);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    // Retrieve all medications for a specific user
    Cursor readAllData(String userId){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Column_USERID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{userId});
        return cursor;
    }

    // Update medication data
    void updateData(String row_id, String name, String dosage, String frequency, String startDate, String endDate, String timeOfDay, String notes, boolean reminderEnabled){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_TITLE, name);
        cv.put(Column_DOSAGE, dosage);
        cv.put(Column_FREQUENCY, frequency);
        cv.put(Column_STARTDATE, startDate);
        cv.put(Column_ENDDATE, endDate);
        cv.put(Column_TIMEOF_DAY, timeOfDay);
        cv.put(Column_NOTES, notes);
        cv.put(Column_REMINDER_ENABLED, reminderEnabled ? 1 : 0);

        long result = db.update(TABLE_NAME, cv, "_id =?", new String[] {row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Successfully Updated.", Toast.LENGTH_LONG).show();
        }
    }

    // Delete a specific medication record
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Deletion Failed.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Deletion Success.", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete all medication records
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    // Add a new user
    void addUser(String username, String password, String salt){
        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_USER, username);
        cv.put(Column_PASSWORD, password);
        cv.put("salt", salt);

        long result = myDb.insert(USER_TABLE, null, cv);
        if(result == -1){
            Toast.makeText(context, "User Creation Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "User Creation Successful", Toast.LENGTH_SHORT).show();
        }
    }

    // Check if username is unique
    boolean checkUserUnique(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + Column_USER + " = ?", new String[]{username});
        return cursor.getCount() > 0;
    }
    // Add a new tag
    void addTag(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_TAG_NAME, name);

        long result = db.insert(TAG_TABLE, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to add tag.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Tag added successfully.", Toast.LENGTH_SHORT).show();
        }
    }

    // Retrieve all tags
    Cursor readAllTags() {
        String query = "SELECT * FROM " + TAG_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    // Associate a medication with a tag
    void addMedicationTagAssociation(int medicationId, int tagId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_MEDICATION_ID, medicationId);
        cv.put(Column_TAG_ID_ASSOCIATION, tagId);

        long result = db.insert(MEDICATION_TAG_ASSOCIATION_TABLE, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to associate tag with medication.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Tag associated with medication successfully.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteMedicationTagAssociation(int medicationId, int tagId) {
        SQLiteDatabase db = this.getWritableDatabase();


        int rowsAffected = db.delete(MEDICATION_TAG_ASSOCIATION_TABLE,
                Column_MEDICATION_ID + " = ? AND " + Column_TAG_ID_ASSOCIATION + " = ?",
                new String[]{String.valueOf(medicationId), String.valueOf(tagId)});


        if (rowsAffected > 0) {
            Toast.makeText(context, "Association deleted successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to delete association.", Toast.LENGTH_SHORT).show();
        }
    }


    // Retrieves the stored hash and salt for a given username
    public String[] getUserCredentials(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password_hash, salt FROM users WHERE username = ?", new String[]{username});

        if (cursor.moveToFirst()) {
            String hash = cursor.getString(0);
            String salt = cursor.getString(1);
            cursor.close();
            return new String[]{hash, salt};
        } else {
            cursor.close();
            return null; // User not found
        }
    }

    // Retrieve all tags associated with a medication
    Cursor readTagsForMedication(int medicationId) {
        String query = "SELECT " + TAG_TABLE + "." + Column_TAG_NAME +
                " FROM " + TAG_TABLE +
                " INNER JOIN " + MEDICATION_TAG_ASSOCIATION_TABLE +
                " ON " + TAG_TABLE + "." + Column_TAG_ID + " = " + MEDICATION_TAG_ASSOCIATION_TABLE + "." + Column_TAG_ID_ASSOCIATION +
                " WHERE " + MEDICATION_TAG_ASSOCIATION_TABLE + "." + Column_MEDICATION_ID + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(medicationId)});
        return cursor;
    }

    // Check user login credentials
    boolean checkUser(String username, String pwd){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + Column_USER + " = ?" + " AND " + Column_PASSWORD + " = ?", new String[]{username, pwd});
        return cursor.getCount() > 0;
    }
}
