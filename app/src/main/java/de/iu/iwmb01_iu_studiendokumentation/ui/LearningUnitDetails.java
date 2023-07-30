package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.adapter.LearningEffortAdapter;
import de.iu.iwmb01_iu_studiendokumentation.db.LearningEffortDataSource;
import de.iu.iwmb01_iu_studiendokumentation.db.LearningUnitDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.LearningEffort;
import de.iu.iwmb01_iu_studiendokumentation.model.LearningUnit;

public class LearningUnitDetails extends AppCompatActivity {

    private LearningEffortDataSource learningEffortDataSource;

    LearningEffortAdapter learningEffortAdapter;

    LearningUnit learningUnit;

    private ArrayList<LearningEffort> learningEfforts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_unit_details);

        learningEffortDataSource = new LearningEffortDataSource(this);

        if (savedInstanceState != null) {
            learningUnit = (LearningUnit) savedInstanceState.getSerializable("LEARNING_UNIT_OBJECT");

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("LEARNING_UNIT_OBJECT", learningUnit);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (learningUnit == null) {
            learningUnit = (LearningUnit) getIntent().getSerializableExtra("LEARNING_UNIT_OBJECT");
        }

        learningEfforts = learningEffortDataSource.getLearningEffortsForLearningUnit(learningUnit.getLearningUnitId());

        setLearningUnitTextViews();
        setLearningEffortCurrentTextView();
        initializeRecyclerView();

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

    public void addLearningEffortButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditLearningEffort.class);
        intent.putExtra("MODE", "NEW");
        intent.putExtra("LEARNING_UNIT_OBJECT", learningUnit);
        startActivity(intent);
    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.learningUnitDetailsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        learningEffortAdapter = new LearningEffortAdapter(learningEfforts, learningUnit);
        recyclerView.setAdapter(learningEffortAdapter);
    }

    private void setLearningEffortCurrentTextView() {
        long actualLearningEffortsSum = 0;

        for (LearningEffort learningEffort : learningEfforts) {
            actualLearningEffortsSum += learningEffort.getActualLearningEffort();
        }
        int sumHours =  LearningUnit.calculateLearningEffortHours(actualLearningEffortsSum);
        int sumMinutes = LearningUnit.calculateLearningEffortMinutes(actualLearningEffortsSum);

        TextView currentLearningEffortTextView = findViewById(R.id.learningEffortCurrentTextView);
        currentLearningEffortTextView.setText(this.getString(R.string.current_dp, sumHours, sumMinutes));
    }

    @Override
    protected void onDestroy() {

        learningEffortDataSource.close();
        super.onDestroy();
    }
}