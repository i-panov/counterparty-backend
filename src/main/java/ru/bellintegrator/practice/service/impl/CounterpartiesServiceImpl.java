package ru.bellintegrator.practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.dao.CounterpartiesDAO;
import ru.bellintegrator.practice.mapper.*;
import ru.bellintegrator.practice.service.*;
import ru.bellintegrator.practice.view.*;

import java.util.List;
import java.util.stream.Collectors;

import static ru.bellintegrator.practice.mapper.CounterpartyMapper.*;
import static ru.bellintegrator.practice.mapper.PersonMapper.*;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class CounterpartiesServiceImpl implements CounterpartiesService {
    private final CounterpartiesDAO dao;

    @Autowired
    public CounterpartiesServiceImpl(CounterpartiesDAO dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CounterpartyView> getConterparties() {
        return dao.getConterparties().stream().map(CounterpartyMapper::map).collect(Collectors.toList());
    }

    public CounterpartyDetailView getConterparty(Long id) {
        return mapDetail(dao.getConterparty(id));
    }

    @Transactional
    @Override
    public void addCounterparty(CounterpartyDetailView counterparty) {
        dao.addCounterparty(map(counterparty));
    }

    @Transactional
    @Override
    public void deleteCounterparty(Long id) {
        dao.deleteCounterparty(id);
    }

    @Transactional
    @Override
    public void changeLegalEntity(Long counterpartyId, Long legalEntityId) {
        dao.changeLegalEntity(counterpartyId, legalEntityId);
    }

    @Transactional
    @Override
    public void addContactPerson(Long counterpartyId, PersonView person) {
        dao.addContactPerson(counterpartyId, map(person));
    }

    @Transactional
    @Override
    public void changePerson(PersonView person) {
        dao.changePerson(map(person));
    }

    @Transactional
    @Override
    public void deleteContactPerson(Long counterpartyId, Long contactPersonId) {
        dao.deleteContactPerson(counterpartyId, contactPersonId);
    }
}
