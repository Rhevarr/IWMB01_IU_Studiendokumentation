package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.model.Course;

public class CourseDetails extends AppCompatActivity {

    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        course = (Course) getIntent().getSerializableExtra("COURSE_OBJECT");

    }

    @Override
    protected void onResume() {
        super.onResume();

        setCourseTextViews();
    }
    private void setCourseTextViews() {

        TextView courseTitleTextView = findViewById(R.id.courseTitleTextView);
        TextView courseDescriptionTextView = findViewById(R.id.courseDescriptionTextView);
        TextView courseSemesterTextView = findViewById(R.id.courseSemesterTextView);

        courseTitleTextView.setText(course.getCourseTitle());
        courseDescriptionTextView.setText(this.getString(R.string.description_dp, course.getCourseDescription()));
        courseSemesterTextView.setText(this.getString(R.string.semester_dp, course.getCourseSemester()));
    }

    public void backButtonClicked(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {

   //     coursesDataSource.close();
        super.onDestroy();
    }

}