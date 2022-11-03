package com.sakamoto.simprunjee.dao.interfaces;

import java.util.HashMap;
import java.util.List;

public interface IBaseDAO<T> {
    boolean save(T t) throws IllegalArgumentException;

    boolean update(T t) throws IllegalArgumentException;

    boolean delete(int id);

    boolean delete(T t) throws IllegalArgumentException;

    boolean deleteAll(String field, Object value) throws IllegalArgumentException;

    void deleteAll();

    T find(Integer id);

    T find(String field, Object value) throws IllegalArgumentException;
    T find(HashMap<String, Object> conditions) throws IllegalArgumentException;

    List<T> findAll(String key, Object value) throws IllegalArgumentException;

    List<T> findAll(HashMap<String, Object> conditions) throws IllegalArgumentException;

    List<T> getAll();

    int count();
}
