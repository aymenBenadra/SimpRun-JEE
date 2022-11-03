package com.sakamoto.simprunjee.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "promos", schema = "simprun")
public class PromoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "year")
    private int year;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "formateurID", referencedColumnName = "id", nullable = false)
    private UserEntity formateur;
    @OneToMany(mappedBy = "promo", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Collection<BriefEntity> briefs;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "promo_apprenants",
            joinColumns = @JoinColumn(name = "promoID"),
            inverseJoinColumns = @JoinColumn(name = "apprenantID"))
    private Collection<UserEntity> apprenants;

    public PromoEntity() {
    }

    public PromoEntity(String name, int year, UserEntity formateur) {
        this.name = name;
        this.year = year;
        this.formateur = formateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setFormateur(UserEntity formateur) {
        this.formateur = formateur;
    }

    public Collection<BriefEntity> getBriefs() {
        return briefs;
    }

    public UserEntity getFormateur() {
        return formateur;
    }

    public Collection<UserEntity> getApprenants() {
        return apprenants;
    }
}
