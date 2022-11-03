package com.sakamoto.simprunjee.dao;

import com.sakamoto.simprunjee.entity.UserEntity;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDAOTest {
    static UserDAO users;
    UserEntity apprenant;

    @BeforeAll
    static void setUp() {
        PersistenceManager.changePersistenceUnit("simprun-test");
        users = new UserDAO();
    }

    @BeforeEach
    void setUpEach() {
        // Create apprenant for promo
        apprenant = new UserEntity("apprenant", "apprenant", "apprenant@simp.run", "apprenant");
        users.save(apprenant);
    }

    @Test
    void findByUsername() {
        assertNotNull(users.findByUsername("apprenant"));
        assertNull(users.findByUsername("not found"));
    }

    @AfterEach
    void tearDownEach() {
        users.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        PersistenceManager.close();
    }
}