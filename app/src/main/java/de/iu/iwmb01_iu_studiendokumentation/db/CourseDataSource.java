package de.iu.iwmb01_iu_studiendokumentation.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.iu.iwmb01_iu_studiendokumentation.model.Course;

public class CourseDataSource {

    private SQLiteDatabase db;
    private final MyDatabaseHelper myDatabaseHelper;
    private Cursor cursor;

    public CourseDataSource(Context context) {
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    // Tabellen und Spaltennamen für die Course-Tabelle
    static final String tableCourse = "course";
    static final String columnCourseId = "course_id";
    static final String columnTitle = "title";
    static final String columnDescription = "description";
    static final String columnSemester = "semester";
    static final String columnCreationDate = "creation_datetime";

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
        close();
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        db = myDatabaseHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + tableCourse,null);

        if (cursor.moveToFirst()) {
            do {
                Course course = cursorToCourse(cursor);
                courses.add(course);
            } while (cursor.moveToNext());
        }
        close();
        return courses;
    }

    public Course getCourse(int courseId) {
        Course course;

            db = myDatabaseHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + tableCourse + " WHERE " + columnCourseId +" = " + courseId,null);

            course = cursorToCourse(cursor);
            close();
            return course;
        }

    private static Course cursorToCourse(Cursor cursor) {
        if (cursor != null) {

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

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
        db = myDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnTitle, course.getCourseTitle());
        values.put(columnDescription, course.getCourseDescription());
        values.put(columnSemester, course.getCourseSemester());

        db.update(tableCourse, values, columnCourseId + " = ?", new String[] {Integer.toString(course.getCourseId())});
        close();
    }

    public void removeCourse(Course course) {
        db = myDatabaseHelper.getWritableDatabase();
        db.delete(tableCourse, columnCourseId + " = ?", new String[] {Integer.toString(course.getCourseId())});
        close();
    }

    public void close() {
        if(cursor != null) {
            cursor.close();
        }
        if(myDatabaseHelper != null) {
            myDatabaseHelper.close();
        }
        if(db != null) {
            db.close();
        }
    }
}
