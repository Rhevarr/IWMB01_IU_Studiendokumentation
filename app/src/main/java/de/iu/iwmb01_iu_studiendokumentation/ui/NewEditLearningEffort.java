package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.LearningEffortDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.LearningEffort;
import de.iu.iwmb01_iu_studiendokumentation.model.LearningUnit;

public class NewEditLearningEffort extends AppCompatActivity {

    private final LearningEffortDataSource learningEffortDataSource = new LearningEffortDataSource(this);
    private String mode;
    private LearningEffort learningEffort;
    private LearningUnit learningUnit;

    private TextView selectedDateTextView;
    private TextView selectedTimeTextView;
    private NumberPicker actualLearningEffortHoursNumberPicker;
    private NumberPicker actualLearningEffortMinutesNumberPicker;

    private Date learningEffortDate;
    private int learningEffortActualHours;
    private int learningEffortActualMinutes;

    private String tempDateString;
    private String tempTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_learning_effort);

        ImageButton learningEffortDeleteButton = findViewById(R.id.deleteLearningEffortButton);
        learningEffortDeleteButton.setVisibility(View.GONE); //Damit der DeleteButton nicht standardmäßig sichtbar ist.

        mode = getIntent().getStringExtra("MODE");
        learningUnit = (LearningUnit) getIntent().getSerializableExtra("LEARNING_UNIT_OBJECT");

        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        selectedTimeTextView = findViewById(R.id.selectedTimeTextView);
        actualLearningEffortHoursNumberPicker = findViewById(R.id.learningEffortActualHoursNumberPicker);
        actualLearningEffortMinutesNumberPicker = findViewById(R.id.learningEffortActualMinutesNumberPicker);

        initializeNumberPickerValues();

        if(mode.equals("EDIT")) {
            Button newEditLearningUnitButton = findViewById(R.id.newEditLearningEffortButton);
            newEditLearningUnitButton.setText(R.string.save_learning_effort);

            learningEffortDeleteButton.setVisibility(View.VISIBLE);

            learningEffort = (LearningEffort) getIntent().getSerializableExtra("LEARNING_EFFORT_OBJECT");

            int actualLearningEffortHours = learningEffort.getActualLearningEffortHours();
            int actualLearningEffortMinutes = learningEffort.getActualLearningEffortMinutes();
            actualLearningEffortHoursNumberPicker.setValue(actualLearningEffortHours);
            actualLearningEffortMinutesNumberPicker.setValue(actualLearningEffortMinutes);

            Date learningEffortDate = learningEffort.getLearningEffortDate();

            SimpleDateFormat sdfDate = new SimpleDateFormat(getString(R.string.sdf_date_format));
            SimpleDateFormat sdfTime = new SimpleDateFormat(getString(R.string.sdf_time_format));
            String timestampDateString = String.format(getString(R.string.date_dp),sdfDate.format(learningEffortDate));
            String timestampTimeString = String.format(getString(R.string.time_dp),sdfTime.format(learningEffortDate));
            selectedDateTextView.setText(timestampDateString);
            selectedTimeTextView.setText(timestampTimeString);
        }
    }

    private void changeDateButtonClicked() {
        Button selectDateButton = findViewById(R.id.changeDateButton);
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewEditLearningEffort.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

                        tempDateString = (selectedYear + "-" + selectedMonth+ "-" + selectedDay)+ " ";
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void changeTimeButtonClicked() {
        Button selectTimeButton = findViewById(R.id.changeTimeButton);
        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Damit dynamisch ermittelt werden kann, ob die Zeitauswahl für den Benutzer im 12- oder 24-Stunden-Modus erfolgt.
                // Da aktuell nur deutsch als alternative Sprache neben englisch angeboten wird, passt das. Ist jedoch vermutlich sauberer lösbar.
                boolean is24HourFormat;
                if (Locale.getDefault().getLanguage().equals("de")) {
                    is24HourFormat = true;
                } else {
                    is24HourFormat = false;
                }
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewEditLearningEffort.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        tempTimeString = (selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, is24HourFormat);
                timePickerDialog.show();
            }
        });
    }

    private void initializeNumberPickerValues() {
        String[] valuesHours = new String[100];
        String[] valuesMinutes = new String[60];

        actualLearningEffortHoursNumberPicker.setMinValue(0);
        actualLearningEffortHoursNumberPicker.setMaxValue(99);

        actualLearningEffortMinutesNumberPicker.setMinValue(0);
        actualLearningEffortMinutesNumberPicker.setMaxValue(59);

        for(int i = 0; i < 100; i++) {
            valuesHours[i] = String.format("%02d", i);
        }
        actualLearningEffortHoursNumberPicker.setDisplayedValues(valuesHours);

        for(int j = 0; j < 60; j++) {
            valuesMinutes[j] = String.format("%02d", j);
        }
        actualLearningEffortMinutesNumberPicker.setDisplayedValues(valuesMinutes);
    }

    public void saveLearningEffortButtonClicked(View view) {

        if(areFieldsEmpty()) {
            String message = getResources().getString(R.string.toast_error_edittexts_empty);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {

            // todo: date und time values holen

            learningEffortActualHours =  actualLearningEffortHoursNumberPicker.getValue();
            learningEffortActualMinutes = actualLearningEffortMinutesNumberPicker.getValue();

            if(mode.equals("NEW")) {
                newSaveLearningEffort();
            } else if(mode.equals("EDIT")) {
                editSaveLearningEffort();
            }
        }
    }

    private void newSaveLearningEffort() {
        learningEffortDataSource.addLearningEffort(learningEffortDate, learningEffortActualHours, learningEffortActualMinutes, learningUnit.getLearningUnitId());
        String message = getResources().getString(R.string.toast_new_learning_unit);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra("LEARNING_UNIT_OBJECT", learningUnit);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void editSaveLearningEffort() {
        learningEffort.setActualLearningEffort(learningEffortActualHours, learningEffortActualMinutes);
        learningEffort.setLearningEffortDate(learningEffortDate);

        learningEffortDataSource.updateLearningEffort(learningEffort);
        String message = getResources().getString(R.string.toast_edit_learning_effort);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LearningUnitDetails.class);
        intent.putExtra("LEARNING_EFFORT_OBJECT", learningEffort);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }


    private boolean areFieldsEmpty() {
        return selectedDateTextView.getText().toString().equals(getString(R.string.string_placeholder))
                || selectedTimeTextView.getText().toString().equals(getString(R.string.string_placeholder))
                || (actualLearningEffortHoursNumberPicker.getValue() == 0) && (actualLearningEffortMinutesNumberPicker.getValue() == 0);
    }

    public void backButtonClicked(View view) {
        finish();
    }

    public void deleteLearningEffortButtonClicked (View view) {
        learningEffortDataSource.removeLearningEffort(learningEffort);

        String message = getResources().getString(R.string.toast_learning_effort_deleted);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        finish();
    }
    @Override
    protected void onDestroy() {
        learningEffortDataSource.close();
        super.onDestroy();
    }
}