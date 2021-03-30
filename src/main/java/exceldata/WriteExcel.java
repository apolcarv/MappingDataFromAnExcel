package exceldata;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import utils.constants.*;
import static utils.constants.ConstantsGeneral.EXCEL_COLUMN_E_2;

public class WriteExcel {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteExcel.class);

    private WriteExcel() {
        super();
    }

    public static void saveCaseIdExcel(String caseId) throws InvalidFormatException {
        try (
                FileInputStream inputStream = new FileInputStream(new
                    File(ConstantsGeneral.EXCEL_FILE_PATH));
                Workbook workbook = WorkbookFactory.create(inputStream);
                FileOutputStream outputStream = new FileOutputStream(
                        ConstantsGeneral.EXCEL_FILE_PATH);
        ) {
            Sheet sheet = workbook.getSheet(ConstantsGeneral.CASE_SHEET);
            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            Cell cell = row.createCell(EXCEL_COLUMN_E_2);
            cell.setCellValue(caseId);
            workbook.write(outputStream);
        } catch (IOException b) {
            LOGGER.error(ConstantsGeneral.ERROR_LOAD_EXCEL, b);
        }
    }
}
