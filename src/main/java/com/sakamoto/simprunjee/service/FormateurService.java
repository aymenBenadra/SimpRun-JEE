package com.sakamoto.simprunjee.service;

import com.sakamoto.simprunjee.dao.interfaces.IBriefDAO;
import com.sakamoto.simprunjee.dao.interfaces.IPromoDAO;
import com.sakamoto.simprunjee.dao.interfaces.IUserDAO;
import com.sakamoto.simprunjee.entity.BriefEntity;
import com.sakamoto.simprunjee.entity.DeliverableEntity;
import com.sakamoto.simprunjee.entity.PromoEntity;
import com.sakamoto.simprunjee.entity.UserEntity;
import com.sakamoto.simprunjee.entity.enums.BriefStatus;
import com.sakamoto.simprunjee.entity.enums.UserRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FormateurService {
    @Inject
    private IUserDAO apprenants;
    @Inject
    private IBriefDAO briefs;
    @Inject
    private IPromoDAO promos;

    public FormateurService() {}

    public boolean addApprenantToPromo(String username, PromoEntity promo) {
        UserEntity apprenant = apprenants.findByUsername(username);
        if (apprenant != null && promo != null) {
            promo.getApprenants().add(apprenant);
            return promos.update(promo);
        }
        return false;
    }

    public boolean removeApprenantFromPromo(String username, PromoEntity promo) {
        promo.getApprenants().removeIf(apprenant -> apprenant.getUsername().equals(username));
        return promos.update(promo);
    }

    public List<UserEntity> getApprenants(PromoEntity promo) {
        return new ArrayList<>(promo.getApprenants());
    }

    public List<UserEntity> getApprenants() {
        return apprenants.getAll()
                .stream()
                .filter(user -> user.getRole() == UserRoles.Apprenant)
                .filter(user -> user.getPromosApprenant().size() == 0 || user.getPromosApprenant()
                        .stream()
                        .noneMatch(promo -> promo.getYear() == Year.now().getValue()))
                .toList();
    }

    public List<BriefEntity> getBriefs(BriefStatus status) {
        return briefs.findAll("status", status);
    }

    public List<BriefEntity> getBriefs(PromoEntity promo) {
        return briefs.findAll("promo", promo);
    }

    public List<BriefEntity> getBriefs() {
        return briefs.getAll();
    }

    public BriefEntity getBrief(String title) {
        return briefs.findByTitle(title);
    }

    public List<DeliverableEntity> getDeliverables(String briefTitle) {
        BriefEntity brief = briefs.findByTitle(briefTitle);
        if (brief != null) {
            return new ArrayList<>(brief.getDeliverables());
        }
        return null;
    }

    public BriefEntity addBrief(String title, String description, PromoEntity promo, Date deadline) {
        if (promo != null) {
            BriefEntity brief = new BriefEntity(title, description, deadline, promo);
            if (briefs.save(brief)) {
                return brief;
            }
        }
        return null;
    }

    public boolean broadcastBrief(BriefEntity brief) {
        String mailBody = "New brief is added by your formateur " + brief.getPromo().getFormateur().getUsername()
                + "\nBrief Title: " + brief.getTitle()
                + "\nBrief Description: " + brief.getDescription()
                + "\nBrief Deadline: " + brief.getDeadline();

        for (UserEntity apprenant : getApprenants(brief.getPromo())) {
            if (!EmailService.getInstance().sendEmail(apprenant.getEmail(), "New Brief added to your promo", mailBody)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeBrief(String title) {
        BriefEntity brief = briefs.findByTitle(title);
        if (brief != null) {
            return briefs.delete(brief);
        }
        return false;
    }

    public boolean archiveBrief(String title) {
        BriefEntity brief = briefs.findByTitle(title);
        if (brief != null) {
            brief.setStatus(BriefStatus.Archived);
            return briefs.update(brief);
        }
        return false;
    }

    public boolean activateBrief(String title) {
        BriefEntity brief = briefs.findByTitle(title);
        if (brief != null) {
            brief.setStatus(BriefStatus.Active);
            return briefs.update(brief);
        }
        return false;
    }
}
