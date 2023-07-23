package de.iu.iwmb01_iu_studiendokumentation.model;

import android.database.Cursor;

import de.iu.iwmb01_iu_studiendokumentation.db.ProfileDataSource;

public class Profile {

    private long profileID;
    private String firstName;
    private String lastName;
    private String studyProgram;

    public Profile(long profileID, String firstName, String lastName, String studyProgram) {
        this.profileID = profileID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studyProgram = studyProgram;
    }

    public long getProfileID() {
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
