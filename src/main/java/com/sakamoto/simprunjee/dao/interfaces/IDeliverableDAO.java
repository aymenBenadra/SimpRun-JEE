package com.sakamoto.simprunjee.dao.interfaces;

import com.sakamoto.simprunjee.entity.BriefEntity;
import com.sakamoto.simprunjee.entity.DeliverableEntity;
import com.sakamoto.simprunjee.entity.UserEntity;

import java.util.List;

public interface IDeliverableDAO extends IBaseDAO<DeliverableEntity> {
    List<DeliverableEntity> findByBrief(BriefEntity brief);
    List<DeliverableEntity> findByApprenant(UserEntity apprenant);
    DeliverableEntity find(BriefEntity brief, UserEntity apprenant);
}
