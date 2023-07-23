package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.ProfileDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class NewEditProfile extends AppCompatActivity {

    private final ProfileDataSource profileDataSource = new ProfileDataSource(this);
    private String mode;

    private Profile profile;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText studyProgramEditText;

    private long profileId;
    private String firstName;
    private String lastName;
    private String studyProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_profile);

        mode = getIntent().getStringExtra("MODE");

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        studyProgramEditText = findViewById((R.id.studyProgramEditText));

    if(mode.equals("EDIT")) {
        profileId = getIntent().getLongExtra("PROFILE_ID", - 1);
        profile = profileDataSource.getProfile(profileId);

        firstNameEditText.setText(profile.getFirstName());
        lastNameEditText.setText(profile.getLastName());
        studyProgramEditText.setText(profile.getStudyProgram());
        }
    }


    public void backButtonClicked(View view) {
        finish();
    }

public void saveProfileButtonClicked(View view) {

        if(areEditTextsEmpty()) {
            String message = getResources().getString(R.string.toast_error_edittexts_empty);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
             firstName = firstNameEditText.getText().toString().trim();
             lastName = lastNameEditText.getText().toString().trim();
             studyProgram = studyProgramEditText.getText().toString().trim();

                if(mode.equals("NEW")) {
                    newSaveProfile();
                } else if(mode.equals("EDIT")) {
                    editSaveProfile();
                }
            }
}

private void newSaveProfile() {
    profileDataSource.addProfile(firstName, lastName, studyProgram);
    String message = getResources().getString(R.string.toast_new_profile);
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    Intent intent = new Intent(this, CourseList.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Nötig, damit nicht zurückgekehrt werden kann zu NewEditProfile und Welcome.
    startActivity(intent);
}

private void editSaveProfile() {

        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setStudyProgram(studyProgram);

        profileDataSource.updateProfile(profile);
        String message = getResources().getString(R.string.toast_edit_profile);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        finish();
}

private boolean areEditTextsEmpty() {
        return TextUtils.isEmpty(firstNameEditText.getText().toString().trim())
                || TextUtils.isEmpty(lastNameEditText.getText().toString().trim())
                || TextUtils.isEmpty(studyProgramEditText.getText().toString().trim());
    }

    protected void onDestroy() {
        profileDataSource.close();
        super.onDestroy();
    }
}