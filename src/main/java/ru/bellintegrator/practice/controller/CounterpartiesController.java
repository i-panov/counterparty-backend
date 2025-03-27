package ru.bellintegrator.practice.controller;

import ru.bellintegrator.practice.view.*;

import java.util.List;

public interface CounterpartiesController {
    ResultView<List<CounterpartyView>> getConterparties();

    ResultView<CounterpartyDetailView> getConterparty(Long id);

    ResultView<Void> addCounterparty(CounterpartyDetailView counterparty);

    ResultView<Void> deleteCounterparty(Long id);

    ResultView<Void> changeLegalEntity(Long counterpartyId, Long legalEntityId);

    ResultView<Void> addContactPerson(Long counterpartyId, PersonView person);

    ResultView<Void> changePerson(PersonView person);

    ResultView<Void> deleteContactPerson(Long counterpartyId, Long contactPersonId);
}
