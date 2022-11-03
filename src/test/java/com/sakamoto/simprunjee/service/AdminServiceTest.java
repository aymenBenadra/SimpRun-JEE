package com.sakamoto.simprunjee.service;

import com.sakamoto.simprunjee.dao.PersistenceManager;
import com.sakamoto.simprunjee.dao.PromoDAO;
import com.sakamoto.simprunjee.dao.UserDAO;
import com.sakamoto.simprunjee.dao.interfaces.IPromoDAO;
import com.sakamoto.simprunjee.dao.interfaces.IUserDAO;
import com.sakamoto.simprunjee.entity.enums.UserRoles;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    static AdminService adminService;
    static IUserDAO users;
    static IPromoDAO promos;
    int year = Integer.parseInt((new SimpleDateFormat()).format(new Date(System.currentTimeMillis())));

    @BeforeAll
    static void setUp() {
        PersistenceManager.changePersistenceUnit("simprun-test");
        users = new UserDAO();
        promos = new PromoDAO();
        adminService = new AdminService(promos, users);
    }

    @Test
    void testAddApprenant() {
        assertTrue(adminService.addApprenant("test", "test", "test@simp.run", "test"));
        assertNotNull(users.findByUsername("test"));
        assertEquals(UserRoles.Apprenant, users.findByUsername("test").getRole());
    }

    @Test
    void testAddFormateur() {
        assertTrue(adminService.addFormateur("test", "test", "test@simp.run", "test"));
        assertNotNull(users.findByUsername("test"));
        assertEquals(UserRoles.Formateur, users.findByUsername("test").getRole());
    }

    @Test
    void testAddPromo() {
        assertTrue(adminService.addFormateur("test", "test", "test@simp.run", "test"));
        assertTrue(adminService.addPromo("test", year, "test"));
        assertNotNull(promos.findByNameAndYear("test", year));
    }

    @Test
    void testRemoveApprenant() {
        assertTrue(adminService.addApprenant("test", "test", "test@simp.run", "test"));
        assertTrue(adminService.removeApprenant("test"));
        assertNull(users.findByUsername("test"));
    }

    @Test
    void testRemoveFormateur() {
        assertTrue(adminService.addFormateur("test", "test", "test@simp.run", "test"));
        assertTrue(adminService.removeFormateur("test"));
        assertNull(users.findByUsername("test"));
    }

    @Test
    void testRemovePromo() {
        assertTrue(adminService.addFormateur("test", "test", "test@simp.run", "test"));
        assertTrue(adminService.addPromo("test", year, "test"));
        assertTrue(adminService.removePromo("test", year));
        assertNull(promos.findByNameAndYear("test", year));
    }

    @Test
    void testGetApprenants() {
        assertEquals(0, adminService.getApprenants().size());
        assertTrue(adminService.addApprenant("test", "test", "test@simp.run", "test"));
        assertEquals(1, adminService.getApprenants().size());
    }

    @Test
    void testGetFormateurs() {
        assertEquals(0, adminService.getFormateurs().size());
        assertTrue(adminService.addFormateur("test", "test", "test@simp.run", "test"));
        assertEquals(1, adminService.getFormateurs().size());
    }

    @Test
    void testGetPromos() {
        assertEquals(0, adminService.getPromos().size());
        assertTrue(adminService.addFormateur("test", "test", "test@simp.run", "test"));
        assertTrue(adminService.addPromo("test", year, "test"));
        assertEquals(1, adminService.getPromos().size());
    }

    @AfterEach
    void tearDown() {
        promos.deleteAll();
        users.deleteAll();
    }

    @AfterAll
    static void tearDownAll() {
        PersistenceManager.close();
    }
}