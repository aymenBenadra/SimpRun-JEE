package com.sakamoto.simprunjee.service;

import com.sakamoto.simprunjee.dao.interfaces.IBriefDAO;
import com.sakamoto.simprunjee.dao.interfaces.IDeliverableDAO;
import com.sakamoto.simprunjee.entity.BriefEntity;
import com.sakamoto.simprunjee.entity.DeliverableEntity;
import com.sakamoto.simprunjee.entity.PromoEntity;
import com.sakamoto.simprunjee.entity.UserEntity;
import com.sakamoto.simprunjee.entity.enums.BriefStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ApprenantService {
    private final IBriefDAO briefs;
    private final IDeliverableDAO deliverables;

    @Inject
    public ApprenantService(IBriefDAO briefs, IDeliverableDAO deliverables) {
        this.briefs = briefs;
        this.deliverables = deliverables;
    }

    public List<BriefEntity> getBriefs(PromoEntity promo) {
        HashMap<String, Object> filters = new HashMap<>();
        filters.put("promo", promo);
        filters.put("status", BriefStatus.Active);
        return briefs.findAll(filters);
    }

    public BriefEntity getBrief(String title) {
        return briefs.findByTitle(title);
    }

    public boolean addDeliverable(BriefEntity brief, String link, String message, UserEntity apprenant) {
        if (brief != null && brief.getStatus() == BriefStatus.Active) {
            DeliverableEntity deliverable = new DeliverableEntity( link, apprenant, brief, message);
            return deliverables.save(deliverable);
        }
        return false;
    }
}
