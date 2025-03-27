package ru.bellintegrator.practice.controller.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bellintegrator.practice.controller.PersistController;
import ru.bellintegrator.practice.service.PersistService;
import ru.bellintegrator.practice.view.ResultView;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.bellintegrator.practice.view.ResultView.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/persist", produces = APPLICATION_JSON_VALUE)
public class PersistControllerImpl implements PersistController {
    private final PersistService service;

    @Autowired
    public PersistControllerImpl(PersistService service) {
        this.service = service;
    }

    @Override
    @PostMapping("/importExcel")
    public ResultView<Void> importExcel(@RequestParam MultipartFile file) throws IOException, InvalidFormatException {
        service.importExcel(file.getInputStream());
        return success();
    }

    @Override
    @ResponseBody
    @GetMapping("/exportExcel")
    public HttpEntity<byte[]> exportExcel() throws IOException, IllegalAccessException, NoSuchFieldException {
        byte[] document = service.exportExcel();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        header.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=export.xlsx");
        header.setContentLength(document.length);
        return new HttpEntity<>(document, header);
    }
}
