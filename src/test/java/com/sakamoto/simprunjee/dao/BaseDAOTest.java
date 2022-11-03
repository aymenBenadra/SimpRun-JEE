package com.sakamoto.simprunjee.dao;

import com.sakamoto.simprunjee.entity.UserEntity;
import com.sakamoto.simprunjee.entity.enums.UserRoles;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BaseDAOTest {

    static UserDAO users;

    @BeforeAll
    static void setUp() {
        PersistenceManager.changePersistenceUnit("simprun-test");
        users = new UserDAO();
    }

    @BeforeEach
    void setUpEach() {
        UserEntity user = new UserEntity("test", "test", "test@simp.run", "test");
        users.save(user);
    }

    @Test
    void testSave() {
        UserEntity user2 = new UserEntity("test2", "test2", "test2@simp.run", "test2");

        assertTrue(users.save(user2));
        assertNotNull(users.find(user2.getId()));
    }

    @Test
    void testUpdate() {
        UserEntity user = users.findByUsername("test");
        user.setName("test2");
        assertTrue(users.update(user));
        assertEquals("test2", users.find(user.getId()).getName());
    }

    @Test
    void testDelete() {
        UserEntity user = users.findByUsername("test");
        assertTrue(users.delete(user.getId()));
        assertNull(users.find(user.getId()));
    }

    @Test
    void testDeleteAll() {
        assertTrue(users.deleteAll("role", UserRoles.Apprenant));
        assertEquals(0,users.findAll("role", UserRoles.Apprenant).size());
    }

    @Test
    void testFind() {
        UserEntity user = users.findByUsername("test");
        assertNotNull(users.find(user.getId()));
        assertNull(users.find(-1));
    }

    @Test
    void testFindField() {
        assertNotNull(users.find("username", "test"));
        assertNull(users.find("username", "404"));
    }

    @Test
    void testFindAll() {
        assertEquals(1, users.findAll("role", UserRoles.Apprenant).size());
        assertEquals(0, users.findAll("role", UserRoles.Admin).size());
    }

    @Test
    void testFindAllByFilters() {
        assertEquals(1, users.findAll("role", UserRoles.Apprenant).size());
        assertEquals(0, users.findAll("role", UserRoles.Admin).size());
    }

    @Test
    void testGetAll() {
        assertEquals(1, users.getAll().size());
    }

    @Test
    void testCount() {
        assertEquals(1, users.count());
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