package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.CourseDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.Course;

public class CourseDetails extends AppCompatActivity {

    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

    }

    @Override
    protected void onResume() {
        super.onResume();

        course = (Course) getIntent().getSerializableExtra("COURSE_OBJECT");
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

    public void changeCourseButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditCourse.class);
        intent.putExtra("MODE", "EDIT");
        intent.putExtra("COURSE_OBJECT", course);
        startActivity(intent);
    }

    public void backButtonClicked(View view) {
        finish();
    }

    public void deleteCourseButtonClicked (View view) {
        CourseDataSource courseDataSource = new CourseDataSource(this);
        courseDataSource.removeCourse(course);

        String message = getResources().getString(R.string.toast_course_deleted);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        courseDataSource.close();
        finish();
    }

    public void addLearningUnitButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditLearningUnit.class);
        intent.putExtra("MODE", "NEW");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {

   //     learningUnitDataSource.close();
        super.onDestroy();
    }

}