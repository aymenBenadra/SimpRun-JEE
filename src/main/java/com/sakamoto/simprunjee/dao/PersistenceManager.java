package com.sakamoto.simprunjee.dao;

import com.google.common.base.Preconditions;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;

public class PersistenceManager {
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("simprun", loadProperties("simprun"));
        }
        return entityManagerFactory.createEntityManager();
    }

    private static HashMap<String, String> loadProperties(String persistenceUnitName) {
        Preconditions.checkNotNull(persistenceUnitName);
        Dotenv dotenv = Dotenv.load();
        HashMap<String, String> properties = new HashMap<>();
        if (persistenceUnitName.equals("simprun")) {
            properties.put("jakarta.persistence.jdbc.url", dotenv.get("DATABASE_PRODUCTION_URL"));
            properties.put("jakarta.persistence.jdbc.user", dotenv.get("DATABASE_PRODUCTION_USERNAME"));
            properties.put("jakarta.persistence.jdbc.password", dotenv.get("DATABASE_PRODUCTION_PASSWORD"));
            properties.put("jakarta.persistence.jdbc.driver", dotenv.get("DATABASE_PRODUCTION_DRIVER"));
        } else {
            properties.put("jakarta.persistence.jdbc.url", dotenv.get("DATABASE_TEST_URL"));
            properties.put("jakarta.persistence.jdbc.user", dotenv.get("DATABASE_TEST_USERNAME"));
            properties.put("jakarta.persistence.jdbc.password", dotenv.get("DATABASE_TEST_PASSWORD"));
            properties.put("jakarta.persistence.jdbc.driver", dotenv.get("DATABASE_TEST_DRIVER"));
        }
        return properties;
    }

    public static EntityManager beginTransaction() {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        return entityManager;
    }

    public static void commitTransaction(EntityManager em) {
        em.getTransaction().commit();
    }

    public static void rollbackTransaction(EntityManager em) {
        em.getTransaction().rollback();
    }

    public static void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    public static void changePersistenceUnit(String persistenceUnit) {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit, loadProperties(persistenceUnit));
    }
}
