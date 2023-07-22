package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.iu.iwmb01_iu_studiendokumentation.R;

public class CourseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
    }

    public void profileButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditProfile.class);
        startActivity(intent);
    }
}