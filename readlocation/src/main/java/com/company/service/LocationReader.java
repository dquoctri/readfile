package com.company.service;

import com.company.model.Location;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.List;

public interface LocationReader {
    String REPORT_DATA_SHEET_NAME = "Report Data";

    List<Location> getAllLocationFromSheet(Sheet sheet);
    Sheet getSheetFromFileByName(String fileName, String sheetName) throws IOException, InvalidFormatException;
}
