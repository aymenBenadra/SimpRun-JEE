package com.sakamoto.simprunjee.service;

import com.sakamoto.simprunjee.dao.*;
import com.sakamoto.simprunjee.dao.interfaces.IBriefDAO;
import com.sakamoto.simprunjee.dao.interfaces.IDeliverableDAO;
import com.sakamoto.simprunjee.dao.interfaces.IPromoDAO;
import com.sakamoto.simprunjee.dao.interfaces.IUserDAO;
import com.sakamoto.simprunjee.entity.BriefEntity;
import com.sakamoto.simprunjee.entity.DeliverableEntity;
import com.sakamoto.simprunjee.entity.PromoEntity;
import com.sakamoto.simprunjee.entity.UserEntity;
import com.sakamoto.simprunjee.entity.enums.BriefStatus;
import com.sakamoto.simprunjee.entity.enums.UserRoles;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormateurServiceTest {

    static FormateurService formateurService;
    static IUserDAO users;
    static IPromoDAO promos;
    static IBriefDAO briefs;
    static IDeliverableDAO deliverables;
    int year = Integer.parseInt((new SimpleDateFormat()).format(new Date(System.currentTimeMillis())));

    @BeforeAll
    static void setUp() {
        PersistenceManager.changePersistenceUnit("simprun-test");

        users = new UserDAO();
        promos = new PromoDAO();
        briefs = new BriefDAO();
        deliverables = new DeliverableDAO();

        formateurService = new FormateurService(users, briefs, promos);
    }

    @BeforeEach
    void setUpEach() {
        // Create formateur
        UserEntity formateur = new UserEntity("formateur", "formateur", "formateur@simp.run", "formateur", UserRoles.Formateur);
        users.save(formateur);

        // Create promo
        promos.save(new PromoEntity("test promo", year, formateur));

        // Create brief
        briefs.save(new BriefEntity("test brief", "This is a test brief", new Date(System.currentTimeMillis()), promos.findByNameAndYear("test promo", year)));

        // Create apprenant
        users.save(new UserEntity("apprenant", "apprenant", "apprenant@simp.run", "apprenant"));
    }

    @Test
    void addApprenantToPromo() {
        PromoEntity promo = promos.findByNameAndYear("test promo", year);
        assertEquals(0, promo.getApprenants().size());
        assertTrue(formateurService.addApprenantToPromo("apprenant", promo));
        assertEquals(1, promo.getApprenants().size());
    }

    @Test
    void testRemoveApprenantFromPromo() {
        PromoEntity promo = promos.findByNameAndYear("test promo", year);
        assertTrue(formateurService.addApprenantToPromo("apprenant", promo));
        assertEquals(1, promo.getApprenants().size());
        assertTrue(formateurService.removeApprenantFromPromo("apprenant", promo));
        assertEquals(0, promo.getApprenants().size());
    }

    @Test
    void testGetApprenantsByPromo() {
        PromoEntity promo = promos.findByNameAndYear("test promo", year);
        assertEquals(0, formateurService.getApprenants(promo).size());
        assertTrue(formateurService.addApprenantToPromo("apprenant", promo));
        assertEquals(1, formateurService.getApprenants(promo).size());
    }

    @Test
    void testGetApprenants() {
        assertEquals(1, formateurService.getApprenants().size());
        assertTrue(formateurService.addApprenantToPromo("apprenant", promos.findByNameAndYear("test promo", year)));
        assertEquals(0, formateurService.getApprenants().size());
    }

    @Test
    void testGetBriefsByStatus() {
        assertEquals(1, formateurService.getBriefs(BriefStatus.Active).size());

        PromoEntity promo = promos.findByNameAndYear("test promo", year);
        formateurService.addBrief("test brief 2", "This is a test brief 2", promo, new Date(System.currentTimeMillis() + 100000));

        assertEquals(2, formateurService.getBriefs(BriefStatus.Active).size());
        assertEquals(0, formateurService.getBriefs(BriefStatus.Archived).size());
    }

    @Test
    void testGetBriefsByPromo() {
        PromoEntity promo = promos.findByNameAndYear("test promo", year);

        assertEquals(1, formateurService.getBriefs(promo).size());
        formateurService.addBrief("test brief 2", "This is a test brief 2", promo, new Date(System.currentTimeMillis() + 100000));
        assertEquals(2, formateurService.getBriefs(promo).size());
    }

    @Test
    void testGetBriefs() {
        PromoEntity promo = promos.findByNameAndYear("test promo", year);

        assertEquals(1, formateurService.getBriefs().size());
        formateurService.addBrief("test brief 2", "This is a test brief 2", promo, new Date(System.currentTimeMillis() + 100000));
        assertEquals(2, formateurService.getBriefs().size());
    }

    @Test
    void testGetBrief() {
        BriefEntity brief = briefs.findByTitle("test brief");
        assertEquals(brief.getDescription(), formateurService.getBrief("test brief").getDescription());
    }

    @Test
    void testGetDeliverables() {
        BriefEntity brief = briefs.findByTitle("test brief");
        UserEntity apprenant = users.findByUsername("apprenant");

        assertEquals(0, formateurService.getDeliverables(brief.getTitle()).size());
        deliverables.save(new DeliverableEntity("test link",apprenant, brief,"Testing testing!"));
        assertEquals(1, formateurService.getDeliverables(brief.getTitle()).size());
    }

    @Test
    void testAddBrief() {
        assertEquals(1, briefs.getAll().size());
        formateurService.addBrief("test brief 2", "This is a test brief 2", promos.findByNameAndYear("test promo", year), new Date(System.currentTimeMillis() + 100000));
        assertEquals(2, briefs.getAll().size());
    }

    @Test
    void testBroadcastBrief() {
        BriefEntity brief = briefs.findByTitle("test brief");
        assertTrue(formateurService.broadcastBrief(brief));
    }

    @Test
    void testRemoveBrief() {
        assertEquals(1, briefs.getAll().size());
        assertTrue(formateurService.removeBrief("test brief"));
        assertEquals(0, briefs.getAll().size());
    }

    @Test
    void testArchiveBrief() {
        assertEquals(BriefStatus.Active, briefs.findByTitle("test brief").getStatus());
        assertTrue(formateurService.archiveBrief("test brief"));
        assertEquals(BriefStatus.Archived, briefs.findByTitle("test brief").getStatus());
    }

    @Test
    void testActivateBrief() {
        BriefEntity brief = briefs.findByTitle("test brief");
        brief.setStatus(BriefStatus.Archived);
        briefs.update(brief);

        assertEquals(BriefStatus.Archived, briefs.findByTitle("test brief").getStatus());
        assertTrue(formateurService.activateBrief("test brief"));
        assertEquals(BriefStatus.Active, briefs.findByTitle("test brief").getStatus());
    }

    @AfterEach
    void tearDownEach() {
        deliverables.deleteAll();
        briefs.deleteAll();
        promos.deleteAll();
        users.deleteAll();
    }

    @AfterAll
    static void tearDownAll() {
        PersistenceManager.close();
    }
}