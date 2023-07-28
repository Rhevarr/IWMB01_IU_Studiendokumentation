package de.iu.iwmb01_iu_studiendokumentation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class LearningUnit implements Serializable{
    private final int learningUnitId;
    private final Date creationDate;
    private String learningUnitTitle;

    // Der geplante Leraufwand wird in Minuten gespeichert. Sekunden sind zu fein für die Anforderung, und dadurch reicht auch int aus.
    private int plannedLearningEffort;

    public static int calculateLearningEffort(int hours, int minutes) {
        return (hours * 60) + minutes;
    }

    public LearningUnit(int learningUnitId, Date creationDate, String learningUnitTitle, int plannedLearningEffort) {
        this.learningUnitId = learningUnitId;
        this.creationDate = creationDate;
        this.learningUnitTitle = learningUnitTitle;
        this.plannedLearningEffort = plannedLearningEffort;
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
    public int getPlannedLearningEffort() {
        return plannedLearningEffort;
    }
    public int getPlannedLearningEffortHours() {
        return plannedLearningEffort / 60;
    }

    public int getPlannedLearningEffortMinutes() {
        return plannedLearningEffort % 60;
    }

    public void setPlannedLearningEffort(int plannedLearningEffortHours, int plannedLearningEffortMinutes) {
        this.plannedLearningEffort = calculateLearningEffort(plannedLearningEffortHours, plannedLearningEffortMinutes);
    }

}
