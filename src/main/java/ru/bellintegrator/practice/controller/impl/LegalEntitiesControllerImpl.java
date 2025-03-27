package ru.bellintegrator.practice.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.controller.LegalEntitiesController;
import ru.bellintegrator.practice.service.LegalEntitiesService;
import ru.bellintegrator.practice.view.LegalEntityView;
import ru.bellintegrator.practice.view.ResultView;

import java.util.List;

import static org.springframework.http.MediaType.*;
import static ru.bellintegrator.practice.view.ResultView.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/legalEntities", produces = APPLICATION_JSON_VALUE)
public class LegalEntitiesControllerImpl implements LegalEntitiesController {
    private final LegalEntitiesService service;

    @Autowired
    public LegalEntitiesControllerImpl(LegalEntitiesService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "/getLegalEntities")
    public ResultView<List<LegalEntityView>> getLegalEntities() {
        return success(service.getLegalEntities());
    }
}
