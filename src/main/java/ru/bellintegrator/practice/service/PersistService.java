package ru.bellintegrator.practice.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;

public interface PersistService {
    void importExcel(InputStream input) throws IOException, InvalidFormatException;

    byte[] exportExcel() throws IllegalAccessException, NoSuchFieldException, IOException;
}
