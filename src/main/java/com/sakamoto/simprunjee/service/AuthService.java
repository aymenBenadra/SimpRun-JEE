package com.sakamoto.simprunjee.service;

import com.sakamoto.simprunjee.dao.interfaces.IUserDAO;
import com.sakamoto.simprunjee.entity.UserEntity;

public class AuthService {

    private final IUserDAO users;
    private UserEntity currentUser = null;

    public AuthService(IUserDAO users) {
        this.users = users;
    }

    public boolean login(String username, String password) {
        try {
            UserEntity user = users.findByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UserEntity getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}
