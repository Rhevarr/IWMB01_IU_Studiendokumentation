package de.iu.iwmb01_iu_studiendokumentation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.model.LearningEffort;
import de.iu.iwmb01_iu_studiendokumentation.model.LearningUnit;
import de.iu.iwmb01_iu_studiendokumentation.ui.NewEditLearningEffort;

public class LearningEffortAdapter extends RecyclerView.Adapter<LearningEffortAdapter.LearningEffortViewHolder> {
    private final ArrayList<LearningEffort> learningEfforts;
    private final LearningUnit learningUnit;

    public LearningEffortAdapter(ArrayList<LearningEffort> learningEfforts, LearningUnit learningUnit) {
        this.learningEfforts = learningEfforts;
        this.learningUnit = learningUnit;
    }

    @NonNull
    @Override
    public LearningEffortAdapter.LearningEffortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_learning_effort, parent, false);
        return new LearningEffortAdapter.LearningEffortViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningEffortAdapter.LearningEffortViewHolder holder, int position) {
        LearningEffort learningEffort = learningEfforts.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat(holder.itemView.getContext().getString(R.string.date_time_dp));
        String learningEffortDate = sdf.format(learningEffort.getLearningEffortDate());
        holder.learningEffortDateTextView.setText(learningEffortDate);

        String actualLearningEffort = String.format(holder.itemView.getContext().getString(R.string.learning_effort_time_dp), learningEffort.getActualLearningEffortHours(), learningEffort.getActualLearningEffortMinutes());
        holder.learningEffortActualTextView.setText(actualLearningEffort);

        // Der OnClickListener funktioniert so wie das onClick-Attribut bei den Buttons.
        // Hierbei beziehe ich mich jedoch auf die ImageView innerhalb des Items.
        holder.learningEffortEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), NewEditLearningEffort.class);
                intent.putExtra("LEARNING_UNIT_OBJECT", learningUnit);
                intent.putExtra("LEARNING_EFFORT_OBJECT", learningEffort);
                intent.putExtra("MODE", "EDIT");
                view.getContext().startActivity(intent);

                ((Activity) view.getContext()).overridePendingTransition(R.anim.animation_slide_right_in, R.anim.animation_slide_left_in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return learningEfforts.size();
    }
    public static class LearningEffortViewHolder extends RecyclerView.ViewHolder {
        private final TextView learningEffortDateTextView;
        private final TextView learningEffortActualTextView;
        private final ImageView learningEffortEditImageView;

        public LearningEffortViewHolder(View view) {
            super(view);

            learningEffortDateTextView =  view.findViewById(R.id.itemLearningEffortTimestampTextView);
            learningEffortActualTextView = view.findViewById(R.id.itemActualLearningEffortTextView);
            learningEffortEditImageView = view.findViewById(R.id.itemLearningEffortDetailsImageView);
        }
    }
}
