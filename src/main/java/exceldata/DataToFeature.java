package exceldata;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.constants.ConstantsGeneral.*;



public class DataToFeature {

    private static List<String> textOriginalFeature;

    private DataToFeature() {
        super();
    }

    private static List<String> setExcelDataToFeature(File featureFile)
            throws IOException, InvalidFormatException {
        List<String> fileData = new ArrayList<>();
        try (BufferedReader buffReader = new BufferedReader(
                new InputStreamReader(new BufferedInputStream(new
                        FileInputStream(featureFile)), StandardCharsets.UTF_8))) {

            String data;
            List<Map<String, String>> excelData = new ReaderExcel().getData(
                    EXCEL_FILE_PATH, DATA_TO_TEST_SHEET);
            List<String> originalFeature = new ArrayList<>();

            while ((data = buffReader.readLine()) != null) {
                if (data.trim().contains("|externalData|")) {
                    originalFeature.add("      |externalData|");
                    for (int rowNumber = 0; rowNumber < excelData.size() - 1; rowNumber++) {
                        if (!excelData.get(rowNumber).isEmpty()) {
                            fileData.add("      |" + excelData.get(rowNumber).
                                    get("Caso de prueba") + "|");
                        }
                    }
                } else {
                    fileData.add(data);
                    originalFeature.add(data);
                }
            }
            textOriginalFeature = originalFeature;
        }
        return fileData;
    }

    private static List<File> listOfFeatureFiles(File file) {
        List<File> featureFiles = new ArrayList<>();
        if (file.isFile() && file.getName().endsWith(".feature")) {
            featureFiles.add(file);
        }
        return featureFiles;
    }

    public static void overrideFeatureFiles(String featuresDirectoryPath)
            throws IOException, InvalidFormatException {
        List<File> listOfFeatureFiles = listOfFeatureFiles(new File(featuresDirectoryPath));
        List<String> featureWithExcelData = null;

        for (File featureFile : listOfFeatureFiles) {
            featureWithExcelData = setExcelDataToFeature(featureFile);
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(featureFile),
                            StandardCharsets.UTF_8));) {
                for (String string : featureWithExcelData) {
                    writer.write(string);
                    writer.write("\n");
                }
            }
        }
    }

    public static void overwriteOriginalFeature(String featuresDirectoryPath)
            throws IOException {
        List<File> listOfFeatureFiles = listOfFeatureFiles(new File(featuresDirectoryPath));
        for (File featureFile : listOfFeatureFiles) {
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(featureFile),
                            StandardCharsets.UTF_8));) {
                for (String string : textOriginalFeature) {
                    writer.write(string);
                    writer.write("\n");
                }
            }
        }
    }
}
