package ru.bellintegrator.practice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.dao.*;
import ru.bellintegrator.practice.model.*;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
public class CounterpartiesDAOImpl extends DAOBase implements CounterpartiesDAO {
    @Autowired
    public CounterpartiesDAOImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<Person> getPersons() {
        return getEntities(Person.class);
    }

    @Override
    public void addPersons(List<Person> models) {
        addEntities(models);
    }

    @Override
    public void changePerson(Person person) {
        em.merge(person);
    }

    @Override
    public List<Counterparty> getConterparties() {
        return getEntities(Counterparty.class);
    }

    @Override
    public Counterparty getConterparty(Long id) {
        return em.find(Counterparty.class, id);
    }

    @Override
    public void addCounterparty(Counterparty counterparty) {
        counterparty.setLegalEntity(em.getReference(LegalEntity.class, counterparty.getLegalEntity().getId()));
        em.persist(counterparty);
    }

    @Override
    public void addCounterparties(List<Counterparty> models) {
        models.forEach(this::addCounterparty);
    }

    @Override
    public void deleteCounterparty(Long id) {
        Counterparty counterparty = em.getReference(Counterparty.class, id);
        em.remove(counterparty);
    }

    @Override
    public void changeLegalEntity(Long counterpartyId, Long legalEntityId) {
        Counterparty counterparty = em.getReference(Counterparty.class, counterpartyId);
        counterparty.setLegalEntity(em.getReference(LegalEntity.class, legalEntityId));
        em.merge(counterparty);
    }

    @Override
    public void addContactPerson(Long counterpartyId, Person person) {
        getConterparty(counterpartyId).getContactPersons().add(person);
        em.persist(person);
    }

    @Override
    public void deleteContactPerson(Long counterpartyId, Long contactPersonId) {
        Counterparty counterparty = getConterparty(counterpartyId);
        counterparty.getContactPersons().removeIf(x -> x.getId().equals(contactPersonId));
    }
}
