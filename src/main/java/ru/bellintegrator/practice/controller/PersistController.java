package ru.bellintegrator.practice.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.HttpEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.bellintegrator.practice.view.ResultView;

import java.io.IOException;

public interface PersistController {
    ResultView<Void> importExcel(MultipartFile file) throws IOException, InvalidFormatException;

    HttpEntity<byte[]> exportExcel() throws IOException, IllegalAccessException, NoSuchFieldException;
}
