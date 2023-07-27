package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.db.CourseDataSource;
import de.iu.iwmb01_iu_studiendokumentation.db.LearningUnitDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.Course;
import de.iu.iwmb01_iu_studiendokumentation.model.LearningUnit;

public class LearningUnitDetails extends AppCompatActivity {

    LearningUnit learningUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_unit_details);

        if (savedInstanceState != null) {
            learningUnit = (LearningUnit) savedInstanceState.getSerializable("LEARNING_UNIT_OBJECT");

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("LEARNING_UNIT_OBJECT", learningUnit);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (learningUnit == null) {
            learningUnit = (LearningUnit) getIntent().getSerializableExtra("LEARNING_UNIT_OBJECT");
        }

          setLearningUnitTextViews();

//        learningEfforts = learningEffortDataSource.getLearningEffortsForLearningUnit(learningUnit.getLearningUnitId());
//        initializeRecyclerView();
    }

    private void setLearningUnitTextViews() {

        TextView learningUnitTitleTextView = findViewById(R.id.learningUnitTitleTextView);
        TextView plannedLearningEffortTextView = findViewById(R.id.learningEffortPlannedTextView);

        learningUnitTitleTextView.setText(learningUnit.getLearningUnitTitle());
        plannedLearningEffortTextView.setText(this.getString(R.string.planned_dp, learningUnit.getPlannedLearningEffortHours(), learningUnit.getPlannedLearningEffortMinutes()));
    }

    public void changeLearningUnitButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditLearningUnit.class);
        intent.putExtra("MODE", "EDIT");
        intent.putExtra("LEARNING_UNIT_OBJECT", learningUnit);
        startActivity(intent);
    }

    public void backButtonClicked(View view) {
        finish();
    }

    public void deleteLearningUnitButtonClicked (View view) {
        LearningUnitDataSource learningUnitDataSource = new LearningUnitDataSource(this);
        learningUnitDataSource.removeLearningUnit(learningUnit);

        String message = getResources().getString(R.string.toast_learning_unit_deleted);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        learningUnitDataSource.close();
        finish();
    }
}