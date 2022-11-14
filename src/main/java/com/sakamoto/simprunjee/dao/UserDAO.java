package com.sakamoto.simprunjee.dao;

import com.sakamoto.simprunjee.dao.interfaces.IUserDAO;
import com.sakamoto.simprunjee.entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserDAO extends BaseDAO<UserEntity> implements IUserDAO {
    public UserDAO() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity findByUsername(String username) throws IllegalArgumentException {
        return super.find("username", username);
    }
}
