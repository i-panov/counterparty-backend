package ru.bellintegrator.practice.dao;

import ru.bellintegrator.practice.model.LegalEntity;

import java.util.List;

public interface LegalEntitiesDAO {
    List<LegalEntity> getLegalEntities();
    void addLegalEntities(List<LegalEntity> models);
}
