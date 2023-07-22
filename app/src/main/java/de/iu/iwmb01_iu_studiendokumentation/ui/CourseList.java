package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.MyDatabaseHelper;
import de.iu.iwmb01_iu_studiendokumentation.db.ProfileDataSource;

public class CourseList extends AppCompatActivity {

    private ProfileDataSource profileDataSource = new ProfileDataSource(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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



    public void profileButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditProfile.class);
        startActivity(intent);
    }
}