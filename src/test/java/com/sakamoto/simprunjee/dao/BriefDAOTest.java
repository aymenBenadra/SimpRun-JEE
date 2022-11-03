package com.sakamoto.simprunjee.dao;

import com.sakamoto.simprunjee.entity.BriefEntity;
import com.sakamoto.simprunjee.entity.PromoEntity;
import com.sakamoto.simprunjee.entity.UserEntity;
import com.sakamoto.simprunjee.entity.enums.UserRoles;
import org.junit.jupiter.api.*;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BriefDAOTest {

    static BriefDAO briefs;
    static UserDAO users;
    static PromoDAO promos;

    @BeforeAll
    static void setUp() {
        PersistenceManager.changePersistenceUnit("simprun-test");
        briefs = new BriefDAO();
        users = new UserDAO();
        promos = new PromoDAO();
    }

    @BeforeEach
    void setUpEach() {
        // Create formateur for promo
        UserEntity formateur = new UserEntity("formateur", "formateur", "formateur@simp.run", "formateur", UserRoles.Formateur);
        users.save(formateur);

        // Create promo for brief
        PromoEntity promo = new PromoEntity("test promo", 2021, users.findByUsername("formateur"));
        promos.save(promo);
    }

    @Test
    void testFindByTitle() {
        // Create brief for test
        BriefEntity brief = new BriefEntity("test brief", "test description", new Date(System.currentTimeMillis() + 1000000), promos.findByNameAndYear("test promo", 2021));

        assertTrue(briefs.save(brief));
        assertNotNull(briefs.findByTitle("test brief"));
    }

    @AfterEach
    void tearDownEach() {
        briefs.deleteAll();
        promos.deleteAll();
        users.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        PersistenceManager.close();
    }
}