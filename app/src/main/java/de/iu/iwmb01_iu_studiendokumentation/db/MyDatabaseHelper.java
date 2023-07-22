package de.iu.iwmb01_iu_studiendokumentation.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String dbName = "IUStudyDocumentation.db";
    private static final int dbVersion = 1;

    // Tabelle und Spaltennamen für die Profile Tabelle
    private static final String tableProfile = "profile";
    private static final String columnUserId = "user_id";
    private static final String columnFirstName = "first_name";
    private static final String columnLastName = "last_name";
    private static final String columnStudyProgram = "study_program";

    // SQL-Befehl zum anlegen der Tabelle als String
    private static final String createTableProfile = "CREATE TABLE " + tableProfile + "("
            + columnUserId + " INTEGER PRIMARY KEY,"
            + columnFirstName + " TEXT,"
            + columnLastName + " TEXT,"
            + columnStudyProgram + " TEXT" + ")";


    public MyDatabaseHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableProfile);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Hier könnten Sie zukünftige Migrationslogiken hinzufügen.
        // Zum jetzigen Zeitpunkt können wir die alte Tabelle einfach löschen und neu erstellen.
        db.execSQL("DROP TABLE IF EXISTS " + tableProfile);
        onCreate(db);
    }

    public void saveProfile(String firstName, String lastName, String studyProgram) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnFirstName, firstName);
        values.put(columnLastName, lastName);
        values.put(columnStudyProgram, studyProgram);

        db.insert(tableProfile, null, values);
        db.close();
    }

    public Cursor getProfile() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM" + tableProfile,null);
    }
}
