package com.sakamoto.simprunjee.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "deliverables", schema = "simprun")
@IdClass(DeliverableEntityPK.class)
public class DeliverableEntity {
    @Basic
    @Column(name = "link")
    private String link;
    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;
    @Id
    @Column(name = "apprenantID")
    private int apprenantId;
    @Id
    @Column(name = "briefID")
    private int briefId;
    @Basic
    @Column(name = "message")
    private String message;
    @ManyToOne
    @JoinColumn(name = "apprenantID", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private UserEntity apprenant;
    @ManyToOne
    @JoinColumn(name = "briefID", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private BriefEntity brief;

    public DeliverableEntity() {
    }

    public DeliverableEntity(String link, UserEntity apprenant, BriefEntity brief, String message) {
        this.apprenant = apprenant;
        this.apprenantId = apprenant.getId();
        this.brief = brief;
        this.briefId = brief.getId();
        this.link = link;
        this.message = message;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setApprenant(UserEntity apprenant) {
        this.apprenant = apprenant;
        this.apprenantId = apprenant.getId();
    }

    public UserEntity getApprenant() {
        return apprenant;
    }

    public void setBrief(BriefEntity brief) {
        this.brief = brief;
        this.briefId = brief.getId();
    }

    public BriefEntity getBrief() {
        return brief;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
