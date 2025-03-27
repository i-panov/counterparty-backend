package ru.bellintegrator.practice.view;

import ru.bellintegrator.practice.constraint.*;

import javax.validation.constraints.*;

public class LegalEntityView {
    private Long id;

    /**
     * Название
     */
    @NotNull
    private String name;

    /**
     * ИНН
     */
    @Inn
    @NotNull
    private String inn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }
}
