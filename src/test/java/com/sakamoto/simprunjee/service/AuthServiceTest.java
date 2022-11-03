package com.sakamoto.simprunjee.service;

import com.sakamoto.simprunjee.dao.PersistenceManager;
import com.sakamoto.simprunjee.dao.UserDAO;
import com.sakamoto.simprunjee.entity.UserEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    static AuthService authService;
    static UserDAO userDAO;

    @BeforeAll
    static void setUp() {
        PersistenceManager.changePersistenceUnit("simprun-test");
        userDAO = new UserDAO();
        authService = new AuthService(userDAO);

        UserEntity user = new UserEntity("test", "test", "test@simp.run", "test");
        userDAO.save(user);
    }

    @Test
    void testLogin() {
        assertTrue(authService.login("test", "test"));
        assertEquals("test", authService.getCurrentUser().getUsername());
    }

    @Test
    void testLogout() {
        assertTrue(authService.login("test", "test"));
        authService.logout();
        assertNull(authService.getCurrentUser());
    }

    @AfterEach
    void tearDownEach() {
        authService.logout();
    }

    @AfterAll
    static void tearDown() {
        userDAO.delete(userDAO.findByUsername("test").getId());
    }
}