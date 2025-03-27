package ru.bellintegrator.practice.dao;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class DAOBase {
    protected final EntityManager em;

    public DAOBase(EntityManager em) {
        this.em = em;
    }

    protected <T> List<T> getEntities(Class<T> clazz) {
        String strQuery = String.format("select v from %s as v", clazz.getSimpleName());
        return em.createQuery(strQuery, clazz).getResultList();
    }

    protected <T> void addEntities(List<T> models) {
        models.forEach(em::persist);
    }
}
