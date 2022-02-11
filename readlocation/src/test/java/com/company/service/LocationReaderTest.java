package com.company.service;

import com.company.model.Location;
import com.company.service.impl.LocationReaderImpl;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.ss.usermodel.Sheet;

import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static junit.framework.Assert.*;

public class LocationReaderTest {
    public static final String SAMPLE_XLSX_FILE_PATH = "location_data_2022_02_10.xlsx";
    public static final String EMPTY_DATA_XLSX_FILE_PATH = "location_data_Empty.xlsx";
    public static final String NO_REPORT_SHEET_XLSX_FILE_PATH = "location_data_No_Report_Data_Sheet.xlsx";

    LocationReader locationReader = new LocationReaderImpl();

    @Test
    public void testReadLocationFromSheet() throws IOException, InvalidFormatException {
        List<Location> locations = null;
        Sheet sheet = locationReader.getSheetFromFileByName(SAMPLE_XLSX_FILE_PATH, LocationReader.REPORT_DATA_SHEET_NAME);
        locations = locationReader.getAllLocationFromSheet(sheet);
        assertTrue(locations.size() > 0);
        Location firstLocation = locations.get(0);
        assertEquals("Incorrect Name", "11TUKU", firstLocation.getName());
        assertEquals("Incorrect Serial", "11TUKU", firstLocation.getSerial());
        assertEquals("Incorrect Lat", 50.04826, firstLocation.getCoordinate().x);
        assertEquals("Incorrect getLng", 2.93393, firstLocation.getCoordinate().y);
    }

    @Test
    public void testReadLocationFromEmptySheet() throws IOException, InvalidFormatException {
        List<Location> locations = null;
        Sheet sheet = locationReader.getSheetFromFileByName(EMPTY_DATA_XLSX_FILE_PATH, LocationReader.REPORT_DATA_SHEET_NAME);
        locations = locationReader.getAllLocationFromSheet(sheet);
        assertTrue("Is not empty", locations.isEmpty());
    }

    @Test
    public void testReadLocationNoReportDataSheet() throws IOException, InvalidFormatException {
        List<Location> locations = null;
        Sheet sheet = locationReader.getSheetFromFileByName(NO_REPORT_SHEET_XLSX_FILE_PATH, LocationReader.REPORT_DATA_SHEET_NAME);
        locations = locationReader.getAllLocationFromSheet(sheet);
        assertTrue("Is not empty", locations.isEmpty());
    }

    @Test
    public void testReadLocationFileNotFound() throws IOException, InvalidFormatException {
        Sheet sheet = locationReader.getSheetFromFileByName("FileNotFound.xlsx", LocationReader.REPORT_DATA_SHEET_NAME);
        assertNull("sheet not null", sheet);
    }

    @Test(expected = NotOLE2FileException.class)
    public void testReadLocationInvalidFormat() throws IOException, InvalidFormatException {
        locationReader.getSheetFromFileByName("invalid_format.txt", LocationReader.REPORT_DATA_SHEET_NAME);
    }
}
