package exceldata;

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;

public class ReaderExcel {

    public List<Map<String, String>> getData(String excelFilePath, String sheetName)
            throws IOException, InvalidFormatException {
        Sheet sheet = getSheetByName(excelFilePath, sheetName);
        return readSheet(sheet);
    }

    private Sheet getSheetByName(String excelFilePath, String sheetName)
            throws IOException, InvalidFormatException {
        return getWorkBook(excelFilePath).getSheet(sheetName);
    }

    private Workbook getWorkBook(String excelFilePath) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(new File(excelFilePath));
    }

    private List<Map<String, String>> readSheet(Sheet sheet) {
        Row row;
        int totalRow = sheet.getLastRowNum();
        List<Map<String, String>> excelRows = new ArrayList<>();
        int headerRowNumber = getHeaderRowNumber(sheet);
        if (headerRowNumber != -1) {
            int totalColumn = sheet.getRow(headerRowNumber).getLastCellNum();
            int setCurrentRow = 1;
            for (int currentRow = setCurrentRow; currentRow <= totalRow; currentRow++) {
                row = getRow(sheet, sheet.getFirstRowNum() + currentRow);
                LinkedHashMap<String, String> columnMapdata = new LinkedHashMap<>();
                for (int currentColumn = 0; currentColumn < totalColumn; currentColumn++) {
                    columnMapdata.putAll(getCellValue(sheet, row, currentColumn));
                }
                excelRows.add(columnMapdata);
            }
        }
        return excelRows;
    }

    private int getHeaderRowNumber(Sheet sheet) {
        Row row;
        int totalRow = sheet.getLastRowNum();
        for (int currentRow = 0; currentRow <= totalRow + 1; currentRow++) {
            row = getRow(sheet, currentRow);
            if (row != null) {
                int totalColumn = row.getLastCellNum();
                for (int currentColumn = 0; currentColumn < totalColumn; currentColumn++) {
                    Cell cell;
                    cell = row.getCell(currentColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if (cell.getCellType() == CellType.STRING ||
                            cell.getCellType() == CellType.NUMERIC ||
                            cell.getCellType() == CellType.BOOLEAN) {
                        return row.getRowNum();
                    }
                }
            }
        }
        return (-1);
    }

    private Row getRow(Sheet sheet, int rowNumber) {
        return sheet.getRow(rowNumber);
    }

    private LinkedHashMap<String, String> getCellValue(Sheet sheet, Row row, int currentColumn) {
        LinkedHashMap<String, String> columnMapData = new LinkedHashMap<>();
        CellType typeCurrentCell = sheet.getRow(sheet.getFirstRowNum()).getCell(currentColumn,
                Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                .getCellType();
        Cell cell;

        if (row == null) {
            if (typeCurrentCell != CellType.BLANK) {
                String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).
                        getCell(currentColumn)
                        .getStringCellValue();
                columnMapData.put(columnHeaderName, "");
            }
        } else {
            cell = row.getCell(currentColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (typeCurrentCell != CellType.BLANK) {
                if (cell.getCellType() == CellType.STRING) {
                    String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).
                            getCell(cell.getColumnIndex())
                            .getStringCellValue();
                    columnMapData.put(columnHeaderName, cell.getStringCellValue());
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).
                            getCell(cell.getColumnIndex())
                            .getStringCellValue();
                    columnMapData.put(columnHeaderName, NumberToTextConverter.toText(
                            cell.getNumericCellValue()));
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).
                            getCell(cell.getColumnIndex())
                            .getStringCellValue();
                    columnMapData.put(columnHeaderName, Boolean.toString(
                            cell.getBooleanCellValue()));
                } else if (cell.getCellType() == CellType.ERROR) {
                    String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).
                            getCell(cell.getColumnIndex())
                            .getStringCellValue();
                    columnMapData.put(columnHeaderName, Byte.toString(cell.getErrorCellValue()));
                }
            }
        }
        return columnMapData;
    }
}