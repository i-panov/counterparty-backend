package ru.bellintegrator.practice.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.controller.CounterpartiesController;
import ru.bellintegrator.practice.service.CounterpartiesService;
import ru.bellintegrator.practice.view.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.util.*;

import static org.springframework.http.MediaType.*;
import static ru.bellintegrator.practice.view.ResultView.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/counterparties", produces = APPLICATION_JSON_VALUE)
public class CounterpartiesControllerImpl implements CounterpartiesController {
    private final CounterpartiesService service;

    @Autowired
    public CounterpartiesControllerImpl(CounterpartiesService service) {
        this.service = service;
    }

    @Override
    @GetMapping("/getConterparties")
    public ResultView<List<CounterpartyView>> getConterparties() {
        return success(service.getConterparties());
    }

    @Override
    @GetMapping("/getConterparty/{id:[\\d]+}")
    public ResultView<CounterpartyDetailView> getConterparty(@Valid @NotNull @PathVariable Long id) {
        return success(service.getConterparty(id));
    }

    @Override
    @PostMapping("/addCounterparty")
    public ResultView<Void> addCounterparty(@Valid @RequestBody CounterpartyDetailView counterparty) {
        service.addCounterparty(counterparty);
        return success();
    }

    @Override
    @DeleteMapping("/deleteCounterparty/{id:[\\d]+}")
    public ResultView<Void> deleteCounterparty(@Valid @NotNull @PathVariable Long id) {
        service.deleteCounterparty(id);
        return success();
    }

    @Override
    @PostMapping("/changeLegalEntity/{counterpartyId:[\\d]+}/{legalEntityId:[\\d]+}")
    public ResultView<Void> changeLegalEntity(@PathVariable Long counterpartyId, @PathVariable Long legalEntityId) {
        service.changeLegalEntity(counterpartyId, legalEntityId);
        return success();
    }

    @Override
    @PostMapping("/addContactPerson/{counterpartyId:[\\d]+}")
    public ResultView<Void> addContactPerson(@Valid @NotNull @PathVariable Long counterpartyId, @Valid @NotNull @RequestBody PersonView person) {
        service.addContactPerson(counterpartyId, person);
        return success();
    }

    @Override
    @PostMapping("/changePerson")
    public ResultView<Void> changePerson(@Valid @RequestBody PersonView person) {
        service.changePerson(person);
        return success();
    }

    @Override
    @DeleteMapping("/deleteContactPerson/{counterpartyId:[\\d]+}/{contactPersonId:[\\d]+}")
    public ResultView<Void> deleteContactPerson(@Valid @NotNull @PathVariable Long counterpartyId, @Valid @NotNull @PathVariable Long contactPersonId) {
        service.deleteContactPerson(counterpartyId, contactPersonId);
        return success();
    }
}
