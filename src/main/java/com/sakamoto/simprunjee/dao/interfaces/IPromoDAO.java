package com.sakamoto.simprunjee.dao.interfaces;

import com.sakamoto.simprunjee.entity.PromoEntity;

public interface IPromoDAO extends IBaseDAO<PromoEntity> {
    PromoEntity findByNameAndYear(String name, int year) throws IllegalArgumentException;
}
