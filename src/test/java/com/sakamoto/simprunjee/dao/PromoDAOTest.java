package com.sakamoto.simprunjee.dao;

import com.sakamoto.simprunjee.entity.PromoEntity;
import com.sakamoto.simprunjee.entity.UserEntity;
import com.sakamoto.simprunjee.entity.enums.UserRoles;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PromoDAOTest {
    static PromoDAO promos;
    static UserDAO users;

    @BeforeAll
    static void setUp() {
        PersistenceManager.changePersistenceUnit("simprun-test");
        promos = new PromoDAO();
        users = new UserDAO();
    }

    @Test
    void testFindByNameAndYear() {
        // Create formateur for promo
        UserEntity formateur = new UserEntity("formateur", "formateur", "formateur@simp.run", "formateur", UserRoles.Formateur);
        users.save(formateur);

        // Create promo for test
        PromoEntity promo = new PromoEntity("test promo", 2021, users.findByUsername("formateur"));
        promos.save(promo);

        assertNotNull(promos.findByNameAndYear("test promo", 2021));
        assertNull(promos.findByNameAndYear("not found", 2021));
    }

    @AfterAll
    static void tearDown() {
        promos.deleteAll("true", true);
        users.deleteAll("true", true);
        PersistenceManager.close();
    }
}