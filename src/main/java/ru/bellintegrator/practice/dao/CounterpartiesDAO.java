package ru.bellintegrator.practice.dao;

import ru.bellintegrator.practice.model.*;

import java.util.List;

public interface CounterpartiesDAO {
    List<Person> getPersons();
    void addPersons(List<Person> models);
    void changePerson(Person person);

    List<Counterparty> getConterparties();
    Counterparty getConterparty(Long id);
    void addCounterparty(Counterparty counterparty);
    void addCounterparties(List<Counterparty> models);
    void deleteCounterparty(Long id);

    void changeLegalEntity(Long counterpartyId, Long legalEntityId);

    void addContactPerson(Long counterpartyId, Person person);
    void deleteContactPerson(Long counterpartyId, Long contactPersonId);
}
