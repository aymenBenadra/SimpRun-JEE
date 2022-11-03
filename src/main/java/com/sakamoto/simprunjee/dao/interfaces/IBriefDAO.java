package com.sakamoto.simprunjee.dao.interfaces;

import com.sakamoto.simprunjee.entity.BriefEntity;

public interface IBriefDAO extends IBaseDAO<BriefEntity> {
    BriefEntity findByTitle(String title) throws IllegalArgumentException;
}
