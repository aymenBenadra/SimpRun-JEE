package com.sakamoto.simprunjee.dao;

import com.google.common.base.Preconditions;
import com.sakamoto.simprunjee.dao.interfaces.IBaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;

public class BaseDAO<T> implements IBaseDAO<T> {
    protected final Class<T> type;

    public BaseDAO(Class<T> clazz) {
        this.type = Preconditions.checkNotNull(clazz);
    }

    @Override
    public boolean save(T t) throws IllegalArgumentException {
        Preconditions.checkNotNull(t);
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            em.persist(t);
            PersistenceManager.commitTransaction(em);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return false;
        }
    }

    @Override
    public boolean update(T t) throws IllegalArgumentException {
        Preconditions.checkNotNull(t);
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            em.merge(t);
            PersistenceManager.commitTransaction(em);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            em.remove(em.find(type, id));
            PersistenceManager.commitTransaction(em);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return false;
        }
    }

    @Override
    public boolean delete(T t) throws IllegalArgumentException {
        Preconditions.checkNotNull(t);
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            em.remove(em.contains(t) ? t : em.merge(t));
            PersistenceManager.commitTransaction(em);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return false;
        }
    }

    @Override
    public boolean deleteAll(String field, Object value) throws IllegalArgumentException {
        Preconditions.checkNotNull(field);
        Preconditions.checkNotNull(value);
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            em.createQuery("DELETE FROM " + type.getSimpleName() + " t WHERE " + field + " = :value")
                    .setParameter("value", value)
                    .executeUpdate();
            PersistenceManager.commitTransaction(em);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return false;
        }
    }

    @Override
    public void deleteAll() {
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            em.createQuery("DELETE FROM " + type.getSimpleName()).executeUpdate();
            PersistenceManager.commitTransaction(em);
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
        }
    }

    @Override
    public T find(Integer id) {
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            T t = em.find(type, id);
            PersistenceManager.commitTransaction(em);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return null;
        }
    }

    @Override
    public T find(String field, Object value) throws IllegalArgumentException {
        Preconditions.checkNotNull(field);
        Preconditions.checkNotNull(value);
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            List<T> list = em.createQuery("SELECT t FROM " + type.getSimpleName() + " t WHERE " + field + " = :value", type)
                    .setParameter("value", value)
                    .getResultList();
            PersistenceManager.commitTransaction(em);
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return null;
        }
    }

    @Override
    public T find(HashMap<String, Object> conditions) throws IllegalArgumentException {
        List<T> list = findAll(conditions);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<T> findAll(String key, Object value) throws IllegalArgumentException {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            List<T> list = em.createQuery("SELECT t FROM " + type.getSimpleName() + " t WHERE " + key + " = :value", type)
                    .setParameter("value", value)
                    .getResultList();
            PersistenceManager.commitTransaction(em);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return null;
        }
    }

    @Override
    public List<T> findAll(HashMap<String, Object> conditions) throws IllegalArgumentException {
        Preconditions.checkNotNull(conditions);
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            StringBuilder query = new StringBuilder("SELECT t FROM " + type.getSimpleName() + " t WHERE ");
            for (String key : conditions.keySet()) {
                query.append(key).append(" = :").append(key).append(" AND ");
            }
            query.delete(query.length() - 5, query.length());
            Query q = em.createQuery(query.toString(), type);
            for (String key : conditions.keySet()) {
                q.setParameter(key, conditions.get(key));
            }
            List<T> list = q.getResultList();
            PersistenceManager.commitTransaction(em);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return null;
        }
    }

    @Override
    public List<T> getAll() {
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            List<T> list = em.createQuery("SELECT t FROM " + type.getSimpleName() + " t", type)
                    .getResultList();
            PersistenceManager.commitTransaction(em);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return null;
        }
    }

    @Override
    public int count() {
        EntityManager em = PersistenceManager.beginTransaction();
        try {
            int count = em.createQuery("SELECT count(t) FROM " + type.getSimpleName() + " t", Long.class)
                    .getSingleResult()
                    .intValue();
            PersistenceManager.commitTransaction(em);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            PersistenceManager.rollbackTransaction(em);
            return -1;
        }
    }
}
