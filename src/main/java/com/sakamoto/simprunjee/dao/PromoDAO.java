package com.sakamoto.simprunjee.dao;

import com.sakamoto.simprunjee.dao.interfaces.IPromoDAO;
import com.sakamoto.simprunjee.entity.PromoEntity;

import java.util.HashMap;

public class PromoDAO extends BaseDAO<PromoEntity> implements IPromoDAO {
    public PromoDAO() {
        super(PromoEntity.class);
    }

    @Override
    public PromoEntity findByNameAndYear(String name, int year) throws IllegalArgumentException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("year", year);
        return super.find(params);
    }
}
