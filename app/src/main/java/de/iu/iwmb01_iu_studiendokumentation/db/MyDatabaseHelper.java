package de.iu.iwmb01_iu_studiendokumentation.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String dbName = "IUStudyDocumentation.db";
    private static final int dbVersion = 1;

    public MyDatabaseHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ProfileDataSource.createTableProfile);
        db.execSQL(CourseDataSource.createTableCourse);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // zum aktuellen Zeitpunkt nicht erforderlich
    }


}
