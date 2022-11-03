package com.sakamoto.simprunjee.entity;

import com.sakamoto.simprunjee.entity.enums.UserRoles;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "users", schema = "simprun")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles role;
    @OneToMany(mappedBy = "apprenant", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Collection<DeliverableEntity> deliverables;
    @OneToMany(mappedBy = "formateur", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Collection<PromoEntity> promosFormateur;
    @ManyToMany(mappedBy = "apprenants", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Collection<PromoEntity> promosApprenant;

    public UserEntity() {
    }

    public UserEntity(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = UserRoles.Apprenant;
    }

    public UserEntity(String name, String username, String email, String password, UserRoles role) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Collection<DeliverableEntity> getDeliverables() {
        return deliverables;
    }

    public Collection<PromoEntity> getPromosFormateur() {
        return promosFormateur;
    }

    public Collection<PromoEntity> getPromosApprenant() {
        return promosApprenant;
    }
}
