package com.sakamoto.simprunjee.dao;

import com.sakamoto.simprunjee.dao.interfaces.IBriefDAO;
import com.sakamoto.simprunjee.entity.BriefEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BriefDAO extends BaseDAO<BriefEntity> implements IBriefDAO {
    public BriefDAO() {
        super(BriefEntity.class);
    }

    @Override
    public BriefEntity findByTitle(String title) throws IllegalArgumentException {
        return super.find("title", title);
    }
}
