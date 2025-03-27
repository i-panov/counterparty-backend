package ru.bellintegrator.practice.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExcelTablesWriter {
    private Map<String, List<Map<String, Object>>> tables = new HashMap<>();

    // Добавляет или заменяет список объектов
    public <T> void setModelObjects(List<T> objects) throws IllegalAccessException, NoSuchFieldException {
        if (objects == null || objects.isEmpty())
            return;

        Class clazz = objects.get(0).getClass();
        List<Map<String, Object>> rows = new ArrayList<>();

        // маппим объекты в строки таблицы

        for (T value : objects) {
            rows.add(Convert.objectToTable(value));
        }

        tables.merge(clazz.getSimpleName(), rows, (o, n) -> n);
    }

    // Сохраняет таблицы в переданный поток
    public void save(OutputStream output) throws IOException {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("tables");
        Row tableHeadersRow = sheet.createRow(1);
        Row columnHeadersRow = sheet.createRow(2);

        Integer[] tablesColumnCountList = tables.values().stream().map(t -> t.stream().map(Map::size).max(Integer::compareTo).orElse(0)).toArray(Integer[]::new);
        int index = 1, tableIndex = 0;

        // идем в цикле по таблицам и записываем их в лист книги

        for (Map.Entry<String, List<Map<String, Object>>> table : tables.entrySet()) {
            List<List<Map.Entry<String, Object>>> rows = table.getValue().stream().map(x -> new ArrayList<>(x.entrySet())).collect(Collectors.toList());

            sheet.addMergedRegion(new CellRangeAddress(1, 1, index, index + tablesColumnCountList[tableIndex] - 1));
            Cell tableHeaderCell = tableHeadersRow.createCell(index);
            tableHeaderCell.setCellValue(table.getKey());

            // идем в цикле по строкам

            for (int i = 0; i < rows.size(); i++) {
                Row valuesRow = chooseValue(sheet::getRow, sheet::createRow, i + 3);
                List<Map.Entry<String, Object>> row = rows.get(i);

                // идем в цикле по столбцам

                for (int j = index; j < index + row.size(); j++) {
                    Map.Entry<String, Object> cell = row.get(j - index);

                    Cell columnHeaderCell = chooseValue(columnHeadersRow::getCell, columnHeadersRow::createCell, j);
                    columnHeaderCell.setCellValue(cell.getKey());

                    Cell valueCell = valuesRow.createCell(j);
                    valueCell.setCellValue(cell.getValue().toString());
                }
            }

            index += tablesColumnCountList[tableIndex++] + 1;
        }

        book.write(output);
    }

    private static <P, V> V chooseValue(Function<P, V> left, Function<P, V> right, P param) {
        return Optional.ofNullable(left.apply(param)).orElseGet(() -> right.apply(param));
    }
}
