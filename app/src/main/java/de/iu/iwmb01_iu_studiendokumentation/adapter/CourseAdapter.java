package de.iu.iwmb01_iu_studiendokumentation.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.model.Course;
import de.iu.iwmb01_iu_studiendokumentation.ui.CourseDetails;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private ArrayList<Course> courses;

    public CourseAdapter(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.titleTextView.setText(course.getCourseTitle());
        holder.descriptionTextView.setText(course.getCourseDescription());

        String semesterText = String.format(holder.itemView.getContext().getString(R.string.semester_dp), course.getCourseSemester());
        holder.semesterTextView.setText(semesterText);

        // Der OnClickListener funktioniert so wie das onClick-Attribut bei den Buttons.
        // Hierbei beziehe ich mich jedoch auf die ImageView innerhalb des Items.
        holder.courseDetailsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), CourseDetails.class);
                intent.putExtra("COURSE_OBJECT", course);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final TextView semesterTextView;
        private final ImageView courseDetailsImageView;

        public CourseViewHolder(View view) {
            super(view);

            titleTextView =  view.findViewById(R.id.itemCourseTitleTextView);
            descriptionTextView = view.findViewById(R.id.itemCourseDescriptionTextView);
            semesterTextView = view.findViewById(R.id.itemCourseSemesterTextView);
            courseDetailsImageView = view.findViewById(R.id.itemCourseDetailsImageView);
        }
    }
}
