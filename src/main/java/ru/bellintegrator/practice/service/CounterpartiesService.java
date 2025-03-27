package ru.bellintegrator.practice.service;

import ru.bellintegrator.practice.view.*;

import java.util.List;

public interface CounterpartiesService {
    List<CounterpartyView> getConterparties();

    CounterpartyDetailView getConterparty(Long id);

    void addCounterparty(CounterpartyDetailView counterparty);

    void deleteCounterparty(Long id);

    void changeLegalEntity(Long counterpartyId, Long legalEntityId);

    void addContactPerson(Long counterpartyId, PersonView person);

    void changePerson(PersonView person);

    void deleteContactPerson(Long counterpartyId, Long contactPersonId);
}
