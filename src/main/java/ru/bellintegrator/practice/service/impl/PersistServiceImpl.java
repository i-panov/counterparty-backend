package ru.bellintegrator.practice.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.dao.*;
import ru.bellintegrator.practice.model.*;
import ru.bellintegrator.practice.service.PersistService;
import ru.bellintegrator.practice.util.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class PersistServiceImpl implements PersistService {
    private final LegalEntitiesDAO legalEntitiesDAO;
    private final CounterpartiesDAO counterpartiesDAO;

    private class DatabaseHolder {
        public List<LegalEntity> legalEntities;
        public List<Person> persons;
        public List<Counterparty> counterparties;
        public List<ContactPerson> contactPersons;

        public Optional<Counterparty> mapCounterparty(Counterparty counterparty) {
            counterparty.setContactPersons(contactPersons.stream()
                    .filter(contactPerson -> contactPerson.getCounterpartyId().equals(counterparty.getId()))
                    .map(contactPerson -> persons.stream().filter(person -> person.getId().equals(contactPerson.getPersonId())).findFirst())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()));

            counterparty.setPerson(persons.stream().filter(person -> person.getId().equals(counterparty.getPerson().getId())).findFirst().orElse(null));
            counterparty.setBailee(persons.stream().filter(person -> person.getId().equals(counterparty.getBailee().getId())).findFirst().orElse(null));

            if (counterparty.getPerson() == null || counterparty.getBailee() == null)
                return Optional.empty();

            counterparty.setId(null);
            counterparty.getPerson().setId(null);
            counterparty.getBailee().setId(null);
            counterparty.getContactPersons().forEach(x -> x.setId(null));

            persons.removeAll(counterparty.getContactPersons());
            persons.remove(counterparty.getPerson());
            persons.remove(counterparty.getBailee());

            return Optional.of(counterparty);
        }
    }

    @Autowired
    public PersistServiceImpl(LegalEntitiesDAO legalEntitiesDAO, CounterpartiesDAO counterpartiesDAO) {
        this.legalEntitiesDAO = legalEntitiesDAO;
        this.counterpartiesDAO = counterpartiesDAO;
    }

    @Transactional
    @Override
    public void importExcel(InputStream input) throws IOException, InvalidFormatException {
        ExcelTablesReader reader = ExcelTablesReader.parse(input);
        DatabaseHolder holder = new DatabaseHolder();
        holder.legalEntities = reader.getModelObjects(LegalEntity.class);
        holder.persons = reader.getModelObjects(Person.class);
        holder.counterparties = reader.getModelObjects(Counterparty.class);
        holder.contactPersons = reader.getModelObjects(ContactPerson.class);
        addEntities(holder);
    }

    @Transactional(readOnly = true)
    @Override
    public byte[] exportExcel() throws IllegalAccessException, NoSuchFieldException, IOException {
        ExcelTablesWriter writer = new ExcelTablesWriter();
        DatabaseHolder holder = getEntities();
        writer.setModelObjects(holder.legalEntities);
        writer.setModelObjects(holder.persons);
        writer.setModelObjects(holder.counterparties);
        writer.setModelObjects(holder.contactPersons);

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            writer.save(output);
            return output.toByteArray();
        }
    }

    private DatabaseHolder getEntities() {
        DatabaseHolder holder = new DatabaseHolder();
        holder.legalEntities = legalEntitiesDAO.getLegalEntities();
        holder.persons = counterpartiesDAO.getPersons();
        holder.counterparties = counterpartiesDAO.getConterparties();
        holder.contactPersons = holder.counterparties.stream().flatMap(this::getContactPersons).collect(Collectors.toList());
        return holder;
    }

    private void addEntities(DatabaseHolder holder) {
        holder.legalEntities.forEach(x -> x.setId(null));
        legalEntitiesDAO.addLegalEntities(holder.legalEntities);
        holder.counterparties = holder.counterparties.stream().map(holder::mapCounterparty).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        counterpartiesDAO.addCounterparties(holder.counterparties);
    }

    private Stream<ContactPerson> getContactPersons(Counterparty counterparty) {
        return counterparty.getContactPersons().stream().map(contactPerson -> ContactPerson.of(counterparty.getId(), contactPerson.getId()));
    }
}
