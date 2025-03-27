package ru.bellintegrator.practice.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class ExcelTablesReader {
    private Sheet sheet;
    private int index = 0;
    private Row tableHeaders;
    private int rowsCount;
    private int columnsCount;
    private CellRangeAddress[] regions;
    private Map<String, List<Map<String, Object>>> tables = new HashMap<>();

    private ExcelTablesReader() {
    }

    // парсит поток и возвращает сформированный ридер
    public static ExcelTablesReader parse(InputStream input) throws IOException, InvalidFormatException {
        ExcelTablesReader result = new ExcelTablesReader();
        Workbook book = WorkbookFactory.create(input);
        result.sheet = book.getSheet("tables");
        result.tableHeaders = result.sheet.getRow(1);
        result.rowsCount = result.sheet.getPhysicalNumberOfRows() + 1;
        result.columnsCount = result.tableHeaders.getLastCellNum();

        if (result.rowsCount < 3 && result.columnsCount < 3) {
            throw new InvalidFormatException("Таблица не заполнена.");
        }

        result.regions = IntStream.range(0, result.sheet.getNumMergedRegions()).boxed()
                .map(i -> result.sheet.getMergedRegion(i)).toArray(CellRangeAddress[]::new);

        while (result.hasNext()) {
            try {
                StringPair<List<Map<String, Object>>> table = result.getNextTable();
                result.tables.put(table.key, table.value);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    // получает список объектов модели заданного типа (если она была в файле)
    public <T> List<T> getModelObjects(Class<T> clazz) {
        try {
            List<T> result = new ArrayList<>();

            for (Map<String, Object> table : tables.get(clazz.getSimpleName())) {
                T value = Convert.tableToObject(table, clazz);
                result.add(value);
            }

            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Возникла проблема с этой моделью: " + clazz.getName());
        }
    }

    private Optional<CellRangeAddress> getCellRange(Cell cell) {
        return Optional.ofNullable(cell).flatMap(c -> Arrays.stream(regions).filter(r -> r.isInRange(c.getRowIndex(), c.getColumnIndex())).findFirst());
    }

    private Size getCellSize(Cell cell) {
        Optional<CellRangeAddress> range = getCellRange(cell);
        int width = range.map(x -> x.getLastColumn() - x.getFirstColumn() + 1).orElse(1);
        int height = range.map(x -> x.getLastRow() - x.getFirstRow() + 1).orElse(1);
        return Size.of(width, height);
    }

    private Optional<Object> getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                return Optional.of(cell.getNumericCellValue());
            case HSSFCell.CELL_TYPE_BOOLEAN:
            case HSSFCell.CELL_TYPE_STRING:
                return Optional.of(cell.getStringCellValue());
            default:
                return Optional.empty();
        }
    }

    private boolean hasNext() {
        return index < columnsCount;
    }

    private StringPair<List<Map<String, Object>>> getNextTable() throws InvalidFormatException {
        if (!hasNext())
            throw new IndexOutOfBoundsException();

        // пропускаем пустые ячейки в начале строки (должна быть хотя бы одна пустая обязательно)

        Cell tableHeader = tableHeaders.getCell(index);

        while (tableHeader == null && index < columnsCount) {
            tableHeader = tableHeaders.getCell(++index);
        }

        // считаем размеры заголовка таблицы и если он растянут по вертикали - выходим (неверный формат)

        StringPair<List<Map<String, Object>>> result = StringPair.of(tableHeader.getStringCellValue(), new ArrayList<>());
        Size tableHeaderSize = getCellSize(tableHeader);

        if (tableHeaderSize.height > 1) {
            index += tableHeaderSize.width;
            throw new InvalidFormatException("");
        }

        // идем во внешнем цикле по строкам, а во внутреннем по столбцам и формируем результат

        for (int i = 3; i < rowsCount; i++)  {
            Map<String, Object> row = new HashMap<>();

            for (int j = index; j < index + tableHeaderSize.width; j++) {
                Cell columnHeader = sheet.getRow(2).getCell(j);
                Cell columnValue = sheet.getRow(i).getCell(j);

                if (columnValue == null)
                    continue;

                // считаем размеры заголовка столбца и ячейки и если они растянуты (в любом направлении) - выходим (неверный формат)

                if (getCellRange(columnHeader).isPresent() || getCellRange(columnValue).isPresent()) {
                    index += tableHeaderSize.width + 1;
                    throw new InvalidFormatException("");
                }

                String key = columnHeader.getStringCellValue();
                Object value = getCellValue(columnValue).orElseThrow(() -> new InvalidFormatException(""));
                row.put(key, value);
            }

            if (!row.isEmpty())
                result.value.add(row);
        }

        index += tableHeaderSize.width + 1;

        return result;
    }
}
