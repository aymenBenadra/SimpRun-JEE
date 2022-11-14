package com.sakamoto.simprunjee.dao;

import com.sakamoto.simprunjee.dao.interfaces.IDeliverableDAO;
import com.sakamoto.simprunjee.entity.BriefEntity;
import com.sakamoto.simprunjee.entity.DeliverableEntity;
import com.sakamoto.simprunjee.entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class DeliverableDAO extends BaseDAO<DeliverableEntity> implements IDeliverableDAO {
    public DeliverableDAO() {
        super(DeliverableEntity.class);
    }

    @Override
    public List<DeliverableEntity> findByBrief(BriefEntity brief) {
        return super.findAll("brief", brief);
    }

    @Override
    public List<DeliverableEntity> findByApprenant(UserEntity apprenant) {
        return super.findAll("apprenant", apprenant);
    }

    @Override
    public DeliverableEntity find(BriefEntity brief, UserEntity apprenant) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("brief", brief);
        params.put("apprenant", apprenant);
        return super.find(params);
    }
}
