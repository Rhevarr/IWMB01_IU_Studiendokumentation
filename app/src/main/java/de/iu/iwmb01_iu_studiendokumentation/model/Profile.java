package de.iu.iwmb01_iu_studiendokumentation.model;


import java.io.Serializable;

public class Profile implements Serializable {

    private final int profileID;
    private String firstName;
    private String lastName;
    private String studyProgram;

    public Profile(int profileID, String firstName, String lastName, String studyProgram) {
        this.profileID = profileID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studyProgram = studyProgram;
    }

    public int getProfileID() {
        return profileID;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

}
