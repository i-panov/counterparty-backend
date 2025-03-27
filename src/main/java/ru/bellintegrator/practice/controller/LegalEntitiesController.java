package ru.bellintegrator.practice.controller;

import ru.bellintegrator.practice.view.LegalEntityView;
import ru.bellintegrator.practice.view.ResultView;

import java.util.List;

public interface LegalEntitiesController {
    ResultView<List<LegalEntityView>> getLegalEntities();
}
