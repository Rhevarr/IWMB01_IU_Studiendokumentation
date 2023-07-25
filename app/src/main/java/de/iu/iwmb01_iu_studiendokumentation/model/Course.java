package de.iu.iwmb01_iu_studiendokumentation.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.ArrayList;

public class Course {
    private final int courseId;
    private final Date creationDate;
    private String courseTitle;
    private String courseDescription;
    private int courseSemester;

    private ArrayList<LearningUnit> learningUnits;

    public Course(int courseId, Date creationDate, String courseTitle, String courseDescription, int courseSemester) {
        this.courseId = courseId;
        this.creationDate = creationDate;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseSemester = courseSemester;
        this.learningUnits = new ArrayList<LearningUnit>();
    }

    public int getCourseId() {
        return courseId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public int getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(int courseSemester) {
        this.courseSemester = courseSemester;
    }

    public ArrayList<LearningUnit> getLearningUnits() {
        return learningUnits;
    }

    public void setLearningUnits(ArrayList<LearningUnit> learningUnits) {
        this.learningUnits = learningUnits;
    }

}
