package ru.bellintegrator.practice.mapper;

import ru.bellintegrator.practice.model.Person;
import ru.bellintegrator.practice.view.PersonView;

public class PersonMapper {
    public static PersonView map(Person model) {
        PersonView view = new PersonView();

        /*view.id = model.id;
        view.name = model.name;
        view.phone = model.phone;
        view.email = model.email;*/
        view.setId(model.getId());
        view.setName(model.getName());
        view.setPhone(model.getPhone());
        view.setEmail(model.getEmail());

        return view;
    }

    public static Person map(PersonView view) {
        Person model = new Person();

        /*model.id = view.id;
        model.name = view.name;
        model.phone = view.phone;
        model.email = view.email;*/
        model.setId(view.getId());
        model.setName(view.getName());
        model.setEmail(view.getEmail());
        model.setPhone(view.getPhone());

        return model;
    }
}
