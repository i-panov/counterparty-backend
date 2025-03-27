package ru.bellintegrator.practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.dao.LegalEntitiesDAO;
import ru.bellintegrator.practice.mapper.LegalEntityMapper;
import ru.bellintegrator.practice.service.LegalEntitiesService;
import ru.bellintegrator.practice.view.LegalEntityView;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class LegalEntitiesServiceImpl implements LegalEntitiesService {
    private final LegalEntitiesDAO dao;

    @Autowired
    public LegalEntitiesServiceImpl(LegalEntitiesDAO dao) {
        this.dao = dao;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<LegalEntityView> getLegalEntities() {
        return dao.getLegalEntities().stream().map(LegalEntityMapper::map).collect(Collectors.toList());
    }
}
