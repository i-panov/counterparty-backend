package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "counterparty")
public class Counterparty {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @OneToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH}, optional = false)
    @JoinColumn(name = "legal_entity_id")
    private LegalEntity legalEntity;

    @OneToOne(cascade = ALL, optional = false)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne(cascade = ALL, optional = false)
    @JoinColumn(name = "bailee_id")
    private Person bailee;

    @OneToMany(cascade = ALL)
    @JoinTable(
            name = "counterparty_person",
            joinColumns = @JoinColumn(name = "counterparty_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
    )
    private List<Person> contactPersons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Юр. лицо
     */
    public LegalEntity getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
    }

    /**
     * Данные контрагента
     */
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Данные ответственного лица
     */
    public Person getBailee() {
        return bailee;
    }

    public void setBailee(Person bailee) {
        this.bailee = bailee;
    }

    /**
     * Контактные лица
     */
    public List<Person> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<Person> contactPersons) {
        this.contactPersons = contactPersons;
    }
}
