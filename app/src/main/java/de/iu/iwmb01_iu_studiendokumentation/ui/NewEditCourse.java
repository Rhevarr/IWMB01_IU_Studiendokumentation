package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.CourseDataSource;
import de.iu.iwmb01_iu_studiendokumentation.db.ProfileDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.Course;
import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class NewEditCourse extends AppCompatActivity {

    private final CourseDataSource courseDataSource = new CourseDataSource(this);

    private String mode;
    private Course course;

    private EditText courseTitleEditText;
    private EditText courseDescriptionEditText;
    private Spinner semesterSpinner;

    private ArrayAdapter<String> adapter;

    private int courseId;
    private String courseTitle;
    private String courseDescription;
    private int courseSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_course);

        mode = getIntent().getStringExtra("MODE");

        courseTitleEditText = findViewById(R.id.courseTitleEditText);
        courseDescriptionEditText = findViewById(R.id.courseDescriptionEditText);
        semesterSpinner = findViewById(R.id.semesterSpinner);

        initializeSpinnerValues();

        if(mode.equals("EDIT")) {
            Button newEditCourseButton = findViewById(R.id.newEditCourseButton);
            newEditCourseButton.setText(R.string.save_course);

            courseId = getIntent().getIntExtra("COURSE_ID", - 1);
            course = courseDataSource.getCourse(courseId);

            int indexOfCurrentSemester = adapter.getPosition(String.valueOf(course.getCourseSemester()));

            courseTitleEditText.setText(course.getCourseTitle());
            courseDescriptionEditText.setText(course.getCourseDescription());
            semesterSpinner.setSelection(indexOfCurrentSemester);
        }

    }

    private void initializeSpinnerValues() {
        ArrayList<String> values = new ArrayList<>();
        values.add("");

        for(int i = 1; i <= 99; i++) {
            values.add(String.valueOf(i));
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, values);
        semesterSpinner.setAdapter(adapter);
    }

    public void saveCourseButtonClicked(View view) {

        if(areFieldsEmpty()) {
            String message = getResources().getString(R.string.toast_error_edittexts_empty);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            courseTitle = courseTitleEditText.getText().toString().trim();
            courseDescription = courseDescriptionEditText.getText().toString().trim();
            courseSemester = Integer.parseInt(semesterSpinner.getSelectedItem().toString().trim());

            if(mode.equals("NEW")) {
                newSaveCourse();
            } else if(mode.equals("EDIT")) {
                editSaveCourse();
            }
        }
    }

    private void newSaveCourse() {

        courseDataSource.addCourse(courseTitle, courseDescription, courseSemester);
        String message = getResources().getString(R.string.toast_new_course);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, CourseList.class);
        startActivity(intent);
        finish();
    }

    private void editSaveCourse() {

        course.setCourseTitle(courseTitle);
        course.setCourseDescription(courseDescription);
        course.setCourseSemester(courseSemester);

        courseDataSource.updateCourse(course);
        String message = getResources().getString(R.string.toast_edit_course);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        finish();
    }


    private boolean areFieldsEmpty() {
        return TextUtils.isEmpty(courseTitleEditText.getText().toString().trim())
                || TextUtils.isEmpty(courseDescriptionEditText.getText().toString().trim())
                || TextUtils.isEmpty(semesterSpinner.getSelectedItem().toString().trim());
    }

    public void backButtonClicked(View view) {
        finish();
    }
}