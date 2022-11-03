package com.sakamoto.simprunjee.service;

import com.sakamoto.simprunjee.dao.interfaces.IPromoDAO;
import com.sakamoto.simprunjee.dao.interfaces.IUserDAO;
import com.sakamoto.simprunjee.entity.PromoEntity;
import com.sakamoto.simprunjee.entity.UserEntity;
import com.sakamoto.simprunjee.entity.enums.UserRoles;

import java.util.List;

public class AdminService {

    private final IPromoDAO promos;
    private final IUserDAO users;

    public AdminService(IPromoDAO promos, IUserDAO users) {
        this.promos = promos;
        this.users = users;
    }

    public boolean addApprenant(String name, String username, String email, String password) {
        try {
            if (users.findByUsername(username) == null) {
                UserEntity user = new UserEntity(name, username, email, password);
                return users.save(user);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addFormateur(String name, String password, String email, String username) {
        try {
            if (users.findByUsername(username) == null) {
                UserEntity user = new UserEntity(name, username, email, password, UserRoles.Formateur);
                return users.save(user);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addPromo(String name, int year, String formateurName) {
        try {
            UserEntity formateur = users.findByUsername(formateurName);
            PromoEntity promo = new PromoEntity(name, year, formateur);
            if (promos.save(promo)) {
                promo.setFormateur(formateur);
                return promos.update(promo);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeApprenant(String username) {
        try {
            UserEntity user = users.findByUsername(username);
            if (user != null && user.getRole() == UserRoles.Apprenant) {
                return users.delete(user);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeFormateur(String username) {
        try {
            UserEntity user = users.findByUsername(username);
            if (user != null && user.getRole() == UserRoles.Formateur) {
                return users.delete(user);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removePromo(String name, int year) {
        try {
            PromoEntity promo = promos.findByNameAndYear(name, year);
            if (promo != null) {
                return promos.delete(promo);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserEntity> getApprenants() {
        try {
            return users.findAll("role", UserRoles.Apprenant);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserEntity> getFormateurs() {
        try {
            return users.findAll("role", UserRoles.Formateur);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PromoEntity> getPromos() {
        try {
            return promos.getAll();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
