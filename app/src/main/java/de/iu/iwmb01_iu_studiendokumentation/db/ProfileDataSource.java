package de.iu.iwmb01_iu_studiendokumentation.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProfileDataSource {
        private MyDatabaseHelper myDatabaseHelper;

        public ProfileDataSource(Context context) {
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
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnFirstName, firstName);
        values.put(columnLastName, lastName);
        values.put(columnStudyProgram, studyProgram);
        db.insert(tableProfile, null, values);
        db.close();
    }

    public Cursor getProfile() {
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tableProfile,null);
    }
}
