package exceldata;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import models.LoginData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.constants.*;

public class CreateModels {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateModels.class);
    private static List<Map<String, String>> listData =
            setDataModel(ConstantsGeneral.EXCEL_FILE_PATH);

    private static LoginData logindata;

    private CreateModels() {
    }

    public static List<Map<String, String>> setDataModel(String excelFilePath) {
        List<Map<String, String>> listData = null;
        try {
            ReaderExcel readExcel = new ReaderExcel();
            listData = readExcel.getData(excelFilePath, ConstantsGeneral.DATA_TO_TEST_SHEET);
        } catch (IOException | InvalidFormatException ex) {
            LOGGER.error(ConstantsGeneral.ERROR_LOAD_EXCEL, ex);
        }
        return listData;
    }
    public static LoginData setData(int posicion){
        posicion --;
        if(listData != null){
            logindata = new LoginData();
            logindata.setEmail(listData.get(posicion).get(ConstantsGeneral.EMAIL));
            logindata.setPassword(listData.get(posicion).get(ConstantsGeneral.PASSWORD));
            logindata.setTexthome(listData.get(posicion).get(ConstantsGeneral.TEXTHOME));
        }
        return logindata;
    }

}
