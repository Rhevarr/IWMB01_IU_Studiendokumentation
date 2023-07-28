package de.iu.iwmb01_iu_studiendokumentation.model;

import java.util.Date;

public class LearningEffort {

    private final int learningEffortId;
    private final Date creationDate;
    private Date learningEffortDate;
    // Der geplante Leraufwand wird in Minuten gespeichert. Sekunden sind zu fein für die Anforderung, und dadurch reicht auch int aus.
    private int learningEffort;

    public LearningEffort(int learningEffortId, Date creationDate, Date learningEffortDate, int learningEffort) {
        this.learningEffortId = learningEffortId;
        this.creationDate = creationDate;
        this.learningEffortDate = learningEffortDate;
        this.learningEffort = learningEffort;
    }

    public int getLearningEffortId() {
        return learningEffortId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getLearningEffort() {
        return learningEffort;
    }

    public void setLearningEffort(int learningEffort) {
        this.learningEffort = learningEffort;
    }

    public Date getLearningEffortDate() {
        return learningEffortDate;
    }

    public void setLearningEffortDate(Date learningEffortDate) {
        this.learningEffortDate = learningEffortDate;
    }
}
