package ru.bellintegrator.practice.view;

import javax.validation.constraints.*;

public class CounterpartyView {
    /**
     * Id контрагента
     */
    private Long id;

    /**
     * Название фирмы
     */
    @NotNull
    private String name;

    /**
     * Телефон контрагента
     */
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
    private String phone;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
