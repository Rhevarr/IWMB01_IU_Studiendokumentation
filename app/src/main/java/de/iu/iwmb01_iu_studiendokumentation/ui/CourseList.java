package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.CoursesDataSource;
import de.iu.iwmb01_iu_studiendokumentation.db.MyDatabaseHelper;
import de.iu.iwmb01_iu_studiendokumentation.db.ProfileDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.Course;
import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class CourseList extends AppCompatActivity {

    private ProfileDataSource profileDataSource;
    private CoursesDataSource coursesDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        profileDataSource = new ProfileDataSource(this);
   //     coursesDataSource = new CoursesDataSource(this);

        if (!profileDataSource.isProfileCreated()) {
            // Wenn noch kein Profil erstellt wurde, startet stattdessen die WelcomeActivity
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
            finish(); // Beenden der aktuellen Activity, damit der Benutzer nicht zurückkehren kann
        } else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_list);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Profile profile = profileDataSource.getProfile();
  //      List<Course> courses = coursesDataSource.getAllCourses();

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
        startActivity(intent);
    }
}