package ru.bellintegrator.practice.model;

import javax.persistence.*;

@Entity
@Table(name = "legal_entity")
public class LegalEntity {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Basic(optional = false)
    @Column
    private String name;

    @Basic(optional = false)
    @Column
    private String inn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Название
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * ИНН
     */
    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }
}
