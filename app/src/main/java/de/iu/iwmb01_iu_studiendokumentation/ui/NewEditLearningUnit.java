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

    private EditText learningUnitTitleEditText;
    private NumberPicker planedLearningEffortHoursNumberPicker;
    private NumberPicker planedLearningEffortMinutesNumberPicker;

    private ArrayAdapter<String> adapter;

    private int learningUnitId;
    private String learningUnitTitle;
    private long learningEffortPlanned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_learning_unit);

        mode = getIntent().getStringExtra("MODE");

        learningUnitTitleEditText = findViewById(R.id.learningUnitTitleEditText);
        planedLearningEffortHoursNumberPicker = findViewById(R.id.learningEffortPlannedHoursNumberPicker);
        planedLearningEffortMinutesNumberPicker = findViewById(R.id.learningEffortPlannedMinutesNumberPicker);

        initializeNumberPickerValues();

        if(mode.equals("EDIT")) {
            Button newEditLearningUnitButton = findViewById(R.id.newEditLearningUnitButton);
            newEditLearningUnitButton.setText(R.string.save_learning_unit);

            learningUnit = (LearningUnit) getIntent().getSerializableExtra("LEARNING_UNIT_OBJECT");

        // number Picker füllen    long indexOfCurrentSemester = adapter.getPosition(String.valueOf(course.getCourseSemester()));

            learningUnitTitleEditText.setText(learningUnit.getLearningUnitTitle());
        }

    }

    private void initializeNumberPickerValues() {
        String[] valuesHours = new String[100];
        String[] valuesMinutes = new String[60];

        for(int i = 0; i <= 99; i++) {
            valuesMinutes[i] = String.format("%02d", i);
        }
        planedLearningEffortHoursNumberPicker.setDisplayedValues(valuesHours);

        for(int j = 0; j <= 59; j++) {
            valuesMinutes[j] = String.format("%02d", j);
        }
        planedLearningEffortMinutesNumberPicker.setDisplayedValues(valuesMinutes);
    }

    public void saveCourseButtonClicked(View view) {

        if(areFieldsEmpty()) {
            String message = getResources().getString(R.string.toast_error_edittexts_empty);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            learningUnitTitle = learningUnitTitleEditText.getText().toString().trim();

       //  NumberPicker Wert holen   courseSemester = Integer.parseInt(semesterSpinner.getSelectedItem().toString().trim());

            if(mode.equals("NEW")) {
                newSaveCourse();
            } else if(mode.equals("EDIT")) {
                editSaveCourse();
            }
        }
    }

    private void newSaveCourse() {

    }

    private void editSaveCourse() {

    }


    private boolean areFieldsEmpty() {
        return TextUtils.isEmpty(learningUnitTitleEditText.getText().toString().trim())
                || TextUtils.isEmpty(Integer.toString(planedLearningEffortHoursNumberPicker.getValue()).trim())
                || TextUtils.isEmpty(Integer.toString(planedLearningEffortMinutesNumberPicker.getValue()).trim());
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