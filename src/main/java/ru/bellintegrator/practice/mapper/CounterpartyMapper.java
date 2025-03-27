package ru.bellintegrator.practice.mapper;

import ru.bellintegrator.practice.model.*;
import ru.bellintegrator.practice.view.*;

import java.util.stream.Collectors;

public class CounterpartyMapper {
    public static CounterpartyView map(Counterparty model) {
        CounterpartyView view = new CounterpartyView();

        view.setId(model.getId());
        view.setName(model.getLegalEntity().getName());
        view.setPhone(model.getPerson().getPhone());

        return view;
    }

    public static Counterparty map(CounterpartyView view) {
        Counterparty model = new Counterparty();

        model.setId(view.getId());
        model.setLegalEntity(new LegalEntity());
        model.getLegalEntity().setName(view.getName());
        model.setPerson(new Person());
        model.getPerson().setPhone(view.getPhone());

        return model;
    }

    public static CounterpartyDetailView mapDetail(Counterparty model) {
        CounterpartyDetailView view = new CounterpartyDetailView();

        view.setId(model.getId());
        view.setLegalEntity(LegalEntityMapper.map(model.getLegalEntity()));
        view.setPerson(PersonMapper.map(model.getPerson()));
        view.setBailee(PersonMapper.map(model.getBailee()));
        view.setContactPersons(model.getContactPersons().stream().map(PersonMapper::map).collect(Collectors.toList()));

        return view;
    }

    public static Counterparty map(CounterpartyDetailView view) {
        Counterparty model = new Counterparty();

        model.setId(view.getId());
        model.setLegalEntity(new LegalEntity());
        model.setLegalEntity(LegalEntityMapper.map(view.getLegalEntity()));
        model.setPerson(PersonMapper.map(view.getPerson()));
        model.setBailee(PersonMapper.map(view.getBailee()));
        model.setContactPersons(view.getContactPersons().stream().map(PersonMapper::map).collect(Collectors.toList()));

        return model;
    }
}
