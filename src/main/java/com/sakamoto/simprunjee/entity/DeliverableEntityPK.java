package com.sakamoto.simprunjee.entity;


import java.io.Serializable;
import java.util.Objects;

public class DeliverableEntityPK implements Serializable {

    private int apprenantId;
    private int briefId;

    public DeliverableEntityPK() {
    }

    public DeliverableEntityPK(int apprenantId, int briefId) {
        this.apprenantId = apprenantId;
        this.briefId = briefId;
    }

    public int getApprenantId() {
        return apprenantId;
    }

    public void setApprenantId(int apprenantId) {
        this.apprenantId = apprenantId;
    }

    public int getBriefId() {
        return briefId;
    }

    public void setBriefId(int briefId) {
        this.briefId = briefId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliverableEntityPK that = (DeliverableEntityPK) o;
        return apprenantId == that.apprenantId && briefId == that.briefId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(apprenantId, briefId);
    }
}
