package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.ProfileDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class NewEditProfile extends AppCompatActivity {

    private ProfileDataSource profileDataSource;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_profile);

        mode = getIntent().getStringExtra("MODE");

        if(mode.equals("NEW")) {
            newProfileMode();
        } else if(mode.equals("EDIT")) {
            editProfileMode();
        }
    }

    private void newProfileMode(){

    }

    private void editProfileMode(){
        Profile profile = profileDataSource.getProfile();

        EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        EditText studyProgramEditText = findViewById((R.id.studyProgramEditText));

        firstNameEditText.setText(profile.getFirstName());
        lastNameEditText.setText(profile.getLastName());
        studyProgramEditText.setText(profile.getStudyProgram());
    }

    public void backButtonClicked(View view) {
        finish();
    }

public void saveProfile(View view) {

}
}