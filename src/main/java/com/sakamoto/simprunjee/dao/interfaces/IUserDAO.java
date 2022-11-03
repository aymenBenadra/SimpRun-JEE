package com.sakamoto.simprunjee.dao.interfaces;

import com.sakamoto.simprunjee.entity.UserEntity;

public interface IUserDAO extends IBaseDAO<UserEntity> {
    UserEntity findByUsername(String username) throws IllegalArgumentException;
}
