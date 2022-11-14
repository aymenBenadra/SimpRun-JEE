package com.sakamoto.simprunjee.service;

import com.sakamoto.simprunjee.dao.interfaces.IUserDAO;
import com.sakamoto.simprunjee.entity.UserEntity;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;

import java.io.Serializable;

@SessionScoped
public class AuthService implements Serializable {

    @Inject
    private IUserDAO users;
    private UserEntity currentUser = null;

    public AuthService() {}

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
