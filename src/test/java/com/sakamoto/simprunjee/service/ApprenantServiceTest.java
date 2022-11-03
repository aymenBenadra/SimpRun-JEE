package com.sakamoto.simprunjee.service;

import com.sakamoto.simprunjee.dao.*;
import com.sakamoto.simprunjee.dao.interfaces.IBriefDAO;
import com.sakamoto.simprunjee.dao.interfaces.IDeliverableDAO;
import com.sakamoto.simprunjee.dao.interfaces.IPromoDAO;
import com.sakamoto.simprunjee.dao.interfaces.IUserDAO;
import com.sakamoto.simprunjee.entity.BriefEntity;
import com.sakamoto.simprunjee.entity.PromoEntity;
import com.sakamoto.simprunjee.entity.UserEntity;
import com.sakamoto.simprunjee.entity.enums.UserRoles;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApprenantServiceTest {
    static ApprenantService apprenantService;
    static IUserDAO users;
    static IBriefDAO briefs;
    static IDeliverableDAO deliverables;
    static IPromoDAO promos;
    int year = Integer.parseInt((new SimpleDateFormat()).format(new Date(System.currentTimeMillis())));

    @BeforeAll
    static void setUp() {
        PersistenceManager.changePersistenceUnit("simprun-test");
        users = new UserDAO();
        briefs = new BriefDAO();
        deliverables = new DeliverableDAO();
        promos = new PromoDAO();
        apprenantService = new ApprenantService(briefs, deliverables);
    }

    @BeforeEach
    void setUpEach() {
        // Create formateur
        UserEntity formateur = new UserEntity("formateur", "formateur", "formateur@simp.run", "formateur", UserRoles.Formateur);
        users.save(formateur);

        // Create promo
        PromoEntity promo = new PromoEntity("test promo", year, formateur);
        promos.save(promo);

        // Create brief
        BriefEntity brief = new BriefEntity("test brief", "This is a test brief", new Date(System.currentTimeMillis() + 1000000), promo);
        briefs.save(brief);
    }

    @Test
    void testGetBriefs() {
        assertEquals(1, apprenantService.getBriefs(promos.findByNameAndYear("test promo", year)).size());
        assertEquals("test brief", apprenantService.getBriefs(promos.findByNameAndYear("test promo", year)).get(0).getTitle());
    }

    @Test
    void testGetBrief() {
        assertEquals("test brief", apprenantService.getBrief("test brief").getTitle());
        assertEquals("This is a test brief", apprenantService.getBrief("test brief").getDescription());
    }

    @Test
    void testAddDeliverable() {
        UserEntity apprenant = new UserEntity("apprenant", "apprenant", "apprenant@simp.run", "apprenant", null);
        users.save(apprenant);

        BriefEntity brief = briefs.findByTitle("test brief");

        assertTrue(apprenantService.addDeliverable(brief, "https://github.com/aymenBenadra/SimpRun-JEE", "This is a test deliverable", apprenant));
        assertEquals("https://github.com/aymenBenadra/SimpRun-JEE", deliverables.find(brief, apprenant).getLink());
        assertEquals("This is a test deliverable", deliverables.find(brief, apprenant).getMessage());
    }

    @AfterEach
    void tearDown() {
        deliverables.deleteAll();
        briefs.deleteAll();
        promos.deleteAll();
        users.deleteAll();
    }

    @AfterAll
    static void tearDownAll() {
        promos.deleteAll();
    }
}
