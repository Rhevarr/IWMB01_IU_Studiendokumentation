package de.iu.iwmb01_iu_studiendokumentation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class LearningUnit implements Serializable{
    private final int learningUnitId;
    private final Date creationDate;
    private String learningUnitTitle;
    private long plannedLearningEffort;

    private ArrayList<LearningEffort> learningEfforts;

    public LearningUnit(int learningUnitId, Date creationDate, String learningUnitTitle, long plannedLearningEffort) {
        this.learningUnitId = learningUnitId;
        this.creationDate = creationDate;
        this.learningUnitTitle = learningUnitTitle;
        this.plannedLearningEffort = plannedLearningEffort;
        this.learningEfforts = new ArrayList<LearningEffort>();
    }

    public int getLearningUnitId() {
        return learningUnitId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getLearningUnitTitle() {
        return learningUnitTitle;
    }

    public void setLearningUnitTitle(String learningUnitTitle) {
        this.learningUnitTitle = learningUnitTitle;
    }

    public long getPlannedLearningEffort() {
        return plannedLearningEffort;
    }

    public void setPlannedLearningEffort(long plannedLearningEffort) {
        this.plannedLearningEffort = plannedLearningEffort;
    }

    public ArrayList<LearningEffort> getLearningEfforts() {
        return learningEfforts;
    }

    public void setLearningEfforts(ArrayList<LearningEffort> learningEfforts) {
        this.learningEfforts = learningEfforts;
    }
}
