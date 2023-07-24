package de.iu.iwmb01_iu_studiendokumentation.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import de.iu.iwmb01_iu_studiendokumentation.model.Course;
import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class CourseDataSource {

    private SQLiteDatabase db;
    private final MyDatabaseHelper myDatabaseHelper;
    private Cursor cursor;

    public CourseDataSource(Context context) {
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    // Tabellen und Spaltennamen für die Course-Tabelle
    private static final String tableCourse = "course";
    private static final String columnCourseId = "course_id";
    private static final String columnTitle = "title";
    private static final String columnDescription = "description";
    private static final String columnSemester = "semester";
    private static final String columnCreationDate = "creation_datetime";

    // SQL-Befehl zum anlegen der Tabelle als String
    public static final String createTableCourse = "CREATE TABLE " + tableCourse + "("
            + columnCourseId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + columnTitle + " TEXT,"
            + columnDescription + " TEXT,"
            + columnSemester + " INTEGER,"
            + columnCreationDate + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";

    public void addCourse(String title, String description, int semester) {
        db = myDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnTitle, title);
        values.put(columnDescription, description);
        values.put(columnSemester, semester);
        db.insert(tableCourse, null, values);
    }

    public ArrayList<Course> getAllCourses() {
        return null;
    }

    public Course getCourse(int courseId) {
            db = myDatabaseHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + tableCourse + columnCourseId +" = " + courseId,null);
            return cursorToCourse(cursor);
        }

    public static Course cursorToCourse(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {

            int courseIDIndex = cursor.getColumnIndexOrThrow(columnCourseId);
            int creationDateIndex = cursor.getColumnIndex(columnCreationDate);
            int titleIndex = cursor.getColumnIndex(columnTitle);
            int descriptionIndex = cursor.getColumnIndex(columnDescription);
            int semesterIndex = cursor.getColumnIndex(columnSemester);

            int courseId = cursor.getInt(courseIDIndex);
            String creationDateString = cursor.getString(creationDateIndex);
            String title = cursor.getString(titleIndex);
            String description = cursor.getString(descriptionIndex);
            int semester = cursor.getInt(semesterIndex);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date creationDate = null;

            try {
                creationDate = sdf.parse(creationDateString);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            return new Course(courseId, creationDate, title, description, semester);
        }
        return null;
    }

    public void updateCourse(Course course) {

    }
}
