package com.sakamoto.simprunjee.dao;

import com.sakamoto.simprunjee.entity.BriefEntity;
import com.sakamoto.simprunjee.entity.DeliverableEntity;
import com.sakamoto.simprunjee.entity.PromoEntity;
import com.sakamoto.simprunjee.entity.UserEntity;
import com.sakamoto.simprunjee.entity.enums.UserRoles;
import org.junit.jupiter.api.*;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliverableDAOTest {

    static DeliverableDAO deliverableDAO;
    static BriefDAO briefs;
    static UserDAO users;
    static PromoDAO promos;

    UserEntity apprenant;
    BriefEntity brief;
    DeliverableEntity deliverable;

    @BeforeAll
    static void setUp() {
        PersistenceManager.changePersistenceUnit("simprun-test");
        deliverableDAO = new DeliverableDAO();
        briefs = new BriefDAO();
        users = new UserDAO();
        promos = new PromoDAO();
    }

    @BeforeEach
    void setUpEach() {
        // Create apprenant for promo
        UserEntity formateur = new UserEntity("formateur", "formateur", "formateur@simp.run", "formateur", UserRoles.Formateur);
        users.save(formateur);

        // Create promo for brief
        PromoEntity promo = new PromoEntity("test promo", 2021, formateur);
        promos.save(promo);

        // Create brief for test
        brief = new BriefEntity("test brief", "test description", new Date(System.currentTimeMillis() + 1000000), promo);
        briefs.save(brief);

        // Create apprenant for test
        apprenant = new UserEntity("apprenant", "apprenant", "apprenant@simp.run", "apprenant");
        users.save(apprenant);

        System.out.println("Brief id: " + brief.getId());
        System.out.println("Apprenant id: " + apprenant.getId());

        // Create deliverable for test
        deliverable = new DeliverableEntity("test link", apprenant, brief, "test Description");
    }

    @Test
    void testFindByBrief() {
        assertTrue(deliverableDAO.save(deliverable));
        assertNotNull(deliverableDAO.findByBrief(brief));
    }

    @Test
    void testFindByApprenant() {
        assertTrue(deliverableDAO.save(deliverable));
        assertNotNull(deliverableDAO.findByApprenant(apprenant));
    }

    @Test
    void testFindByComposite() {
        assertTrue(deliverableDAO.save(deliverable));
        assertNotNull(deliverableDAO.find(brief, apprenant));
    }

    @AfterEach
    void tearDownEach() {
        deliverableDAO.deleteAll();
        briefs.deleteAll();
        users.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        PersistenceManager.close();
    }
}