package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.LearningEffortDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.LearningUnit;
import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class NewLearningEffortStopwatch extends AppCompatActivity {

    private LearningEffortDataSource learningEffortDataSource;

    private LearningUnit learningUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_learning_effort_stopwatch);

        learningEffortDataSource = new LearningEffortDataSource(this);

        if (savedInstanceState != null) {
            learningUnit = (LearningUnit) getIntent().getSerializableExtra("LEARNING_UNIT_OBJECT");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (learningUnit == null) {
            learningUnit = (LearningUnit) getIntent().getSerializableExtra("LEARNING_UNIT_OBJECT");
        }

        setLearningUnitTextViews();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("LEARNING_UNIT_OBJECT", learningUnit);
    }

    private void setLearningUnitTextViews() {

        TextView learningUnitTitleTextView = findViewById(R.id.learningUnitTitleTextView);
        TextView plannedLearningEffortTextView = findViewById(R.id.learningEffortPlannedTextView);

        learningUnitTitleTextView.setText(learningUnit.getLearningUnitTitle());
        plannedLearningEffortTextView.setText(this.getString(R.string.planned_dp, learningUnit.getPlannedLearningEffortHours(), learningUnit.getPlannedLearningEffortMinutes()));
    }

    public void backButtonClicked(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        learningEffortDataSource.close();
        super.onDestroy();
    }
}