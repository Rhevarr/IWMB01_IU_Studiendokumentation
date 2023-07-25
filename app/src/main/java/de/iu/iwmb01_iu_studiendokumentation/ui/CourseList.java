package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.adapter.CourseAdapter;
import de.iu.iwmb01_iu_studiendokumentation.db.CourseDataSource;
import de.iu.iwmb01_iu_studiendokumentation.db.ProfileDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.Course;
import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class CourseList extends AppCompatActivity {

    private final ProfileDataSource profileDataSource = new ProfileDataSource(this);
    private final CourseDataSource coursesDataSource = new CourseDataSource(this);

    private Profile profile;

    private ArrayList<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        if (!profileDataSource.isProfileCreated()) {
            // Wenn noch kein Profil erstellt wurde, startet stattdessen die WelcomeActivity
            goToWelcomeActivity();
        } else {
        }

    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.courseListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CourseAdapter adapter = new CourseAdapter(courses);
        recyclerView.setAdapter(adapter);
    }

    private void goToWelcomeActivity() {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
        finish(); // Beenden der aktuellen Activity, damit der Benutzer nicht zurückkehren kann
    }

    @Override
    protected void onResume() {
        super.onResume();

        setProfileTextViews();

       courses = coursesDataSource.getAllCourses();
       initializeRecyclerView();

        // Hier können Sie die Daten in Ihren UI-Elementen anzeigen
    }

    @Override
    protected void onDestroy() {

        profileDataSource.close();
//        coursesDataSource.close();
        super.onDestroy();
    }

    public void profileButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditProfile.class);
        intent.putExtra("MODE", "EDIT");
        intent.putExtra("PROFILE_ID", profile.getProfileID());
        startActivity(intent);
    }

    public void addCourseButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditCourse.class);
        intent.putExtra("MODE", "NEW");
        startActivity(intent);
    }

    private void setProfileTextViews() {
        profile = profileDataSource.getProfile();

        TextView userFullNameTextView = findViewById(R.id.userFullNameTextView);
        TextView userStudyProgramTextView = findViewById((R.id.userStudyProgramTextView));

        String fullName = profile.getFirstName() + " " + profile.getLastName();
        userFullNameTextView.setText(fullName);
        userStudyProgramTextView.setText(profile.getStudyProgram());
    }
}