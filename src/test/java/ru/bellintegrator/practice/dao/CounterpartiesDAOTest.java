package ru.bellintegrator.practice.dao;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.model.*;

import java.util.*;

import static org.junit.Assert.*;
import static ru.bellintegrator.practice.util.SequenceHelper.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class CounterpartiesDAOTest {
    @Autowired
    private CounterpartiesDAO dao;

    private final Comparator<Person> personComparator = Comparator.comparing((Person p) -> p.getName()).thenComparing(p -> p.getPhone()).thenComparing(p -> p.getEmail());

    private final Comparator<Counterparty> counterpartyComparator = Comparator
            .comparing(Counterparty::getPerson, personComparator)
            .thenComparing(Counterparty::getBailee, personComparator)
            .thenComparing(c -> c.getLegalEntity().getId())
            .thenComparing(Counterparty::getContactPersons, createIterableComparator(personComparator));

    private boolean equals(Person left, Person right) {
        return personComparator.compare(left, right) == 0;
    }

    private boolean equals(Counterparty left, Counterparty right) {
        return counterpartyComparator.compare(left, right) == 0;
    }

    private LegalEntity createLegalEntity() {
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setId(1L);
        return legalEntity;
    }

    private Person createPerson() {
        Person person = new Person();
        person.setName("user");
        person.setPhone("+79081234567");
        person.setEmail("user@example.com");
        return person;
    }

    private Counterparty createCounterparty() {
        Counterparty counterparty = new Counterparty();
        counterparty.setLegalEntity(createLegalEntity());
        counterparty.setPerson(createPerson());
        counterparty.setBailee(createPerson());
        counterparty.setContactPersons(asMutableList(createPerson(), createPerson()));
        return counterparty;
    }

    private Person getFirstPerson() {
        return dao.getPersons().get(0);
    }

    private Counterparty getFirstCounterparty() {
        return dao.getConterparties().get(0);
    }

    @Test
    public void addAndGetPersons() {
        Person person = createPerson();
        dao.addPersons(Collections.singletonList(person));
        assert equals(person, getFirstPerson());
    }

    @Test
    public void changePerson() {
        Person person = createPerson();
        dao.addPersons(Collections.singletonList(person));
        person.setEmail("admin@example.com");
        dao.changePerson(person);
        assert equals(person, getFirstPerson());
    }

    @Test
    public void addAndGetCounterparty() {
        Counterparty counterparty = createCounterparty();
        dao.addCounterparty(counterparty);
        assert equals(counterparty, dao.getConterparty(getFirstCounterparty().getId()));
    }

    @Test
    public void addAndGetConterparties() {
        List<Counterparty> counterparties = Arrays.asList(createCounterparty(), createCounterparty());
        dao.addCounterparties(counterparties);
        assert createIterableComparator(counterpartyComparator).compare(counterparties, dao.getConterparties()) == 0;
    }

    @Test
    public void deleteCounterparty() {
        dao.addCounterparty(createCounterparty());
        dao.deleteCounterparty(getFirstCounterparty().getId());
        assert dao.getConterparties().isEmpty();
    }

    @Test
    public void changeLegalEntity() {
        dao.addCounterparty(createCounterparty());
        dao.changeLegalEntity(getFirstCounterparty().getId(), 2L);
        assertEquals(2L, (long) getFirstCounterparty().getLegalEntity().getId());
    }

    @Test
    public void addContactPerson() {
        dao.addCounterparty(createCounterparty());
        Long counterpartyId = getFirstCounterparty().getId();
        dao.addContactPerson(counterpartyId, createPerson());
        assert dao.getConterparty(counterpartyId).getContactPersons().size() == 3;
    }

    @Test
    public void deleteContactPerson() {
        dao.addCounterparty(createCounterparty());
        Counterparty counterparty = getFirstCounterparty();
        dao.deleteContactPerson(counterparty.getId(), counterparty.getContactPersons().get(0).getId());
        assert dao.getConterparty(counterparty.getId()).getContactPersons().size() == 1;
    }
}
