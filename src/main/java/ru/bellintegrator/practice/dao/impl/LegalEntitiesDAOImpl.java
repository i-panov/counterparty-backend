package ru.bellintegrator.practice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.dao.DAOBase;
import ru.bellintegrator.practice.dao.LegalEntitiesDAO;
import ru.bellintegrator.practice.model.LegalEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import java.util.List;

@Repository
public class LegalEntitiesDAOImpl extends DAOBase implements LegalEntitiesDAO {
    @Autowired
    public LegalEntitiesDAOImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<LegalEntity> getLegalEntities() {
        return getEntities(LegalEntity.class);
    }

    @Override
    public void addLegalEntities(List<LegalEntity> models) {
        addEntities(models);
    }
}
