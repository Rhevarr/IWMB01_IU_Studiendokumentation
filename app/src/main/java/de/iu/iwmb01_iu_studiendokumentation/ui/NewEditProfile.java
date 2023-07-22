package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.iu.iwmb01_iu_studiendokumentation.R;

public class NewEditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_profile);
    }

    public void backButtonClicked(View view) {
        Intent intent = new Intent(this, CourseList.class);
        startActivity(intent);
    }

public void saveProfile(View view) {

}
}