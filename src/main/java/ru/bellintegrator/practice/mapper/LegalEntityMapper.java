package ru.bellintegrator.practice.mapper;

import ru.bellintegrator.practice.model.LegalEntity;
import ru.bellintegrator.practice.view.LegalEntityView;

public class LegalEntityMapper {
    public static LegalEntityView map(LegalEntity model) {
        LegalEntityView view = new LegalEntityView();

        view.setId(model.getId());
        view.setName(model.getName());
        view.setInn(model.getInn());

        return view;
    }

    public static LegalEntity map(LegalEntityView view) {
        LegalEntity model = new LegalEntity();

        model.setId(view.getId());
        model.setName(view.getName());
        model.setInn(view.getInn());

        return model;
    }
}
