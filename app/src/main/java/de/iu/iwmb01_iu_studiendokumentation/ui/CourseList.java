package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.CourseDataSource;
import de.iu.iwmb01_iu_studiendokumentation.db.ProfileDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.Course;
import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class CourseList extends AppCompatActivity {

    private ProfileDataSource profileDataSource = new ProfileDataSource(this);
    private CourseDataSource coursesDataSource = new CourseDataSource(this);

    private Profile profile;

    private List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!profileDataSource.isProfileCreated()) {
            // Wenn noch kein Profil erstellt wurde, startet stattdessen die WelcomeActivity
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
            finish(); // Beenden der aktuellen Activity, damit der Benutzer nicht zurückkehren kann
        } else {
            setContentView(R.layout.activity_course_list);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        setProfileTextViews();

        courses = coursesDataSource.getAllCourses();

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