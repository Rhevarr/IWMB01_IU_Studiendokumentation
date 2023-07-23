package de.iu.iwmb01_iu_studiendokumentation.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class ProfileDataSource {

        private SQLiteDatabase db;
        private MyDatabaseHelper myDatabaseHelper;
        private Cursor cursor;
        private final Context context;

    public ProfileDataSource(Context context) {
        this.context = context;
        myDatabaseHelper = new MyDatabaseHelper(context);
    }


        // Tabelle und Spaltennamen für die Profile Tabelle
        private static final String tableProfile = "profile";
        private static final String columnUserId = "user_id";
        private static final String columnFirstName = "first_name";
        private static final String columnLastName = "last_name";
        private static final String columnStudyProgram = "study_program";

        // SQL-Befehl zum anlegen der Tabelle als String
        public static final String createTableProfile = "CREATE TABLE " + tableProfile + "("
                + columnUserId + " INTEGER PRIMARY KEY,"
                + columnFirstName + " TEXT,"
                + columnLastName + " TEXT,"
                + columnStudyProgram + " TEXT" + ")";


    public void saveProfile(String firstName, String lastName, String studyProgram) {
        db = myDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnFirstName, firstName);
        values.put(columnLastName, lastName);
        values.put(columnStudyProgram, studyProgram);
        db.insert(tableProfile, null, values);
    }

    public static Profile cursorToProfile(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {

            int firstNameIndex = cursor.getColumnIndex(columnFirstName);
            int lastNameIndex = cursor.getColumnIndex(columnLastName);
            int studyProgramIndex = cursor.getColumnIndex(columnStudyProgram);

            // cursor.getString verlangt, dass geprüft wird, dass der Index nicht -1 entspricht.
            // Stattdessen könnte man den Index auch statisch setzen, da nur ein Profil existieren sollte, jedoch wäre dies eine unschöne Lösung.
            if (firstNameIndex == -1 || lastNameIndex == -1 || studyProgramIndex == -1) {
                return null;
            }

            String firstName = cursor.getString(firstNameIndex);
            String lastName = cursor.getString(lastNameIndex);
            String studyProgram = cursor.getString(studyProgramIndex);

            return new Profile(firstName, lastName, studyProgram);
        }
        return null;
    }


    public Profile getProfile() {
        db = myDatabaseHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + tableProfile,null);
        return cursorToProfile(cursor);
    }
    public boolean isProfileCreated() {
        db = myDatabaseHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + tableProfile, null);
        int count = cursor.getCount();
        return count > 0;
    }
    public void close() {
        cursor.close();
        myDatabaseHelper.close();
        db.close();
    }
}
