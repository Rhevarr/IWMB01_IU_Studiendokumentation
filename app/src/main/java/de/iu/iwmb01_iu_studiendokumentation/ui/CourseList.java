package de.iu.iwmb01_iu_studiendokumentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.iu.iwmb01_iu_studiendokumentation.R;
import de.iu.iwmb01_iu_studiendokumentation.adapter.CourseAdapter;
import de.iu.iwmb01_iu_studiendokumentation.db.CourseDataSource;
import de.iu.iwmb01_iu_studiendokumentation.db.ProfileDataSource;
import de.iu.iwmb01_iu_studiendokumentation.model.Course;
import de.iu.iwmb01_iu_studiendokumentation.model.Profile;

public class CourseList extends AppCompatActivity {

    private final ProfileDataSource profileDataSource = new ProfileDataSource(this);
    private final CourseDataSource coursesDataSource = new CourseDataSource(this);

    CourseAdapter courseAdapter;

    private Profile profile;

    private ArrayList<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        if (!profileDataSource.isProfileCreated()) {
            // Wenn noch kein Profil erstellt wurde, startet stattdessen die WelcomeActivity
            goToWelcomeActivity();
        }

    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.courseListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseAdapter = new CourseAdapter(courses);
        recyclerView.setAdapter(courseAdapter);
    }

    private void goToWelcomeActivity() {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
        finish(); // Beenden der aktuellen Activity, damit der Benutzer nicht zurückkehren kann
    }

    @Override
    protected void onResume() {
        super.onResume();

        setProfileTextViews();

       courses = coursesDataSource.getAllCourses();
       initializeRecyclerView();
       initializeSortButton();
    }

    private void initializeSortButton() {

        ImageButton sortButton = findViewById(R.id.sortCurseItemImageButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(CourseList.this, sortButton);
                popupMenu.getMenuInflater().inflate(R.menu.sort_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();

                        if (itemId == R.id.option_date_asc) {
                            Collections.sort(courses, new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return c1.getCreationDate().compareTo(c2.getCreationDate());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else if(itemId == R.id.option_title_asc) {
                            Collections.sort(courses, new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return c1.getCourseTitle().compareTo(c2.getCourseTitle());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else if (itemId == R.id.option_semester_asc) {
                            Collections.sort(courses, new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return Integer.compare(c1.getCourseSemester(), c2.getCourseSemester());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else if (itemId == R.id.option_date_desc) {
                            Collections.sort(courses, new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return c2.getCreationDate().compareTo(c1.getCreationDate());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else if(itemId == R.id.option_title_desc) {
                            Collections.sort(courses, new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return c2.getCourseTitle().compareTo(c1.getCourseTitle());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else if (itemId == R.id.option_semester_desc) {
                            Collections.sort(courses, new Comparator<Course>() {
                                @Override
                                public int compare(Course c1, Course c2) {
                                    return Integer.compare(c2.getCourseSemester(), c1.getCourseSemester());
                                }
                            });
                            courseAdapter.notifyDataSetChanged();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }



    @Override
    protected void onDestroy() {

        profileDataSource.close();
        coursesDataSource.close();
        super.onDestroy();
    }

    public void changeProfileButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditProfile.class);
        intent.putExtra("MODE", "EDIT");
        intent.putExtra("PROFILE_ID", profile.getProfileID());
        startActivity(intent);
    }

    public void addCourseButtonClicked(View view) {
        Intent intent = new Intent(this, NewEditCourse.class);
        intent.putExtra("MODE", "NEW");
        startActivity(intent);
    }

    private void setProfileTextViews() {
        profile = profileDataSource.getProfile();

        TextView userFullNameTextView = findViewById(R.id.userFullNameTextView);
        TextView userStudyProgramTextView = findViewById(R.id.userStudyProgramTextView);

        String fullName = profile.getFirstName() + " " + profile.getLastName();
        userFullNameTextView.setText(this.getString(R.string.name_dp, fullName));
        userStudyProgramTextView.setText(this.getString(R.string.study_program_dp, profile.getStudyProgram()));
    }

}