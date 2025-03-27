package ru.bellintegrator.practice.model;

/**
 * Модель для сериализации сводной таблицы контактных лиц.
 */
public class ContactPerson {
    private Long counterpartyId;

    private Long personId;

    public static ContactPerson of(Long counterpartyId, Long personId) {
        ContactPerson result = new ContactPerson();
        result.setCounterpartyId(counterpartyId);
        result.setPersonId(personId);
        return result;
    }

    /**
     * Id контрагента.
     */
    public Long getCounterpartyId() {
        return counterpartyId;
    }

    public void setCounterpartyId(Long counterpartyId) {
        this.counterpartyId = counterpartyId;
    }

    /**
     * Id контактного лица.
     */
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
