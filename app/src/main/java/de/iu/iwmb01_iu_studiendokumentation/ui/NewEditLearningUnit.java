package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.CourseDataSource;
import de.iu.iwmb01_iu_studiendokumentation.db.LearningUnitDataSource;
import de.iu.iwmb01_iu_studiendokumentation.db.ProfileDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.Course;
import de.iu.iwmb01_iu_studiendokumentation.model.LearningUnit;
import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class NewEditLearningUnit extends AppCompatActivity {

    private final LearningUnitDataSource learningUnitDataSource = new LearningUnitDataSource(this);
    private String mode;
    private LearningUnit learningUnit;
    private Course course;

    private EditText learningUnitTitleEditText;
    private NumberPicker planedLearningEffortHoursNumberPicker;
    private NumberPicker planedLearningEffortMinutesNumberPicker;
    private String learningUnitTitle;
    private int learningEffortPlannedHours;
    private int learningEffortPlannedMinutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_learning_unit);

        mode = getIntent().getStringExtra("MODE");
        course = (Course) getIntent().getSerializableExtra("COURSE_OBJECT");

        learningUnitTitleEditText = findViewById(R.id.learningUnitTitleEditText);
        planedLearningEffortHoursNumberPicker = findViewById(R.id.learningEffortPlannedHoursNumberPicker);
        planedLearningEffortMinutesNumberPicker = findViewById(R.id.learningEffortPlannedMinutesNumberPicker);

        initializeNumberPickerValues();

        if(mode.equals("EDIT")) {
            Button newEditLearningUnitButton = findViewById(R.id.newEditLearningUnitButton);
            newEditLearningUnitButton.setText(R.string.save_learning_unit);

            learningUnit = (LearningUnit) getIntent().getSerializableExtra("LEARNING_UNIT_OBJECT");

            int plannedLearningEffortHours = learningUnit.getPlannedLearningEffortHours();
            int plannedLearningEffortMinutes = learningUnit.getPlannedLearningEffortMinutes();

            learningUnitTitleEditText.setText(learningUnit.getLearningUnitTitle());
        }

    }

    private void initializeNumberPickerValues() {
        String[] valuesHours = new String[100];
        String[] valuesMinutes = new String[60];

        planedLearningEffortHoursNumberPicker.setMinValue(0);
        planedLearningEffortHoursNumberPicker.setMaxValue(99);

        planedLearningEffortMinutesNumberPicker.setMinValue(0);
        planedLearningEffortMinutesNumberPicker.setMaxValue(59);

        for(int i = 0; i < 100; i++) {
            valuesHours[i] = String.format("%02d", i);
        }
        planedLearningEffortHoursNumberPicker.setDisplayedValues(valuesHours);

        for(int j = 0; j < 60; j++) {
            valuesMinutes[j] = String.format("%02d", j);
        }
        planedLearningEffortMinutesNumberPicker.setDisplayedValues(valuesMinutes);
    }

    public void saveLearningUnitButtonClicked(View view) {

        if(areFieldsEmpty()) {
            String message = getResources().getString(R.string.toast_error_edittexts_empty);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            learningUnitTitle = learningUnitTitleEditText.getText().toString().trim();

            learningEffortPlannedHours =  planedLearningEffortMinutesNumberPicker.getValue();
            learningEffortPlannedMinutes = planedLearningEffortHoursNumberPicker.getValue();

            if(mode.equals("NEW")) {
                newSaveLearningUnit();
            } else if(mode.equals("EDIT")) {
                editSaveLearningUnit();
            }
        }
    }

    private void newSaveLearningUnit() {
        learningUnitDataSource.addLearningUnit(learningUnitTitle, learningEffortPlannedHours, learningEffortPlannedMinutes, course.getCourseId());
        String message = getResources().getString(R.string.toast_new_learning_unit);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra("COURSE_OBJECT", course);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void editSaveLearningUnit() {
        learningUnit.setLearningUnitTitle(learningUnitTitle);
        learningUnit.setPlannedLearningEffort(learningEffortPlannedHours, learningEffortPlannedMinutes);

        learningUnitDataSource.updateLearningUnit(learningUnit);
        String message = getResources().getString(R.string.toast_edit_learning_unit);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra("LEARNING_UNIT_OBJECT", learningUnit);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }


    private boolean areFieldsEmpty() {
        return TextUtils.isEmpty(learningUnitTitleEditText.getText().toString().trim())
                || (planedLearningEffortHoursNumberPicker.getValue() == 0) && (planedLearningEffortMinutesNumberPicker.getValue() == 0);
    }

    public void backButtonClicked(View view) {
        finish();
    }
    @Override
    protected void onDestroy() {
        learningUnitDataSource.close();
        super.onDestroy();
    }
}