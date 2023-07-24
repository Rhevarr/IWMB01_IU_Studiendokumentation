package de.iu.iwmb01_iu_studiendokumentation.model;

import java.time.LocalDateTime;

public class Course {

    private final long courseId;
    private final LocalDateTime creationDate;
    private String courseTitle;
    private String courseDescription;
    private String courseSemester;

    public Course(long courseId, LocalDateTime creationDate, String courseTitle, String courseDescription, String courseSemester) {
        this.courseId = courseId;
        this.creationDate = creationDate;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseSemester = courseSemester;
    }

    public long getCourseId() {
        return courseId;
    }

    public LocalDateTime getCreationDate() {
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

    public String getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(String courseSemester) {
        this.courseSemester = courseSemester;
    }

}
