package com.sakamoto.simprunjee.entity;

import com.sakamoto.simprunjee.entity.enums.BriefStatus;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "briefs", schema = "simprun")
public class BriefEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "deadline")
    private Date deadline;
    @Basic
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BriefStatus status;
    @ManyToOne
    @JoinColumn(name = "promoID", referencedColumnName = "id", nullable = false)
    private PromoEntity promo;
    @OneToMany(mappedBy = "brief", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Collection<DeliverableEntity> deliverables;

    public BriefEntity() {
    }

    public BriefEntity(String title, String description, Date deadline, PromoEntity promo) {
        this.promo = promo;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = BriefStatus.Active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public BriefStatus getStatus() {
        return status;
    }

    public void setStatus(BriefStatus status) {
        this.status = status;
    }

    public void setPromo(PromoEntity promo) {
        this.promo = promo;
    }

    public PromoEntity getPromo() {
        return promo;
    }

    public Collection<DeliverableEntity> getDeliverables() {
        return deliverables;
    }
}
