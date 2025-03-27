package ru.bellintegrator.practice.view;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class CounterpartyDetailView {
    private Long id;

    /**
     * Id юр. лица
     */
    @NotNull
    private LegalEntityView legalEntity;

    /**
     * Данные контрагента
     */
    @NotNull
    @Valid
    private PersonView person;

    /**
     * Данные ответственного лица
     */
    @NotNull
    private PersonView bailee;

    /**
     * Контактные лица.
     */
    @NotNull
    private List<PersonView> contactPersons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LegalEntityView getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntityView legalEntity) {
        this.legalEntity = legalEntity;
    }

    public PersonView getPerson() {
        return person;
    }

    public void setPerson(PersonView person) {
        this.person = person;
    }

    public PersonView getBailee() {
        return bailee;
    }

    public void setBailee(PersonView bailee) {
        this.bailee = bailee;
    }

    public List<PersonView> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<PersonView> contactPersons) {
        this.contactPersons = contactPersons;
    }
}
