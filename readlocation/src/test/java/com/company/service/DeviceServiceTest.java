package com.company.service;

import com.company.model.Location;
import com.company.service.impl.CountryPolygonFileProvider;
import com.company.service.impl.DeviceServiceImpl;
import com.company.service.impl.LocationReaderImpl;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DeviceServiceTest {

    public static final String SAMPLE_XLSX_FILE_PATH = "location_data_2022_02_10.xlsx";
    LocationReader locationReader = new LocationReaderImpl();
    DeviceService deviceService = new DeviceServiceImpl(new CountryPolygonFileProvider());

    @Test
    public void testCountDeviceInCountry() throws IOException, InvalidFormatException {
        Sheet sheet = locationReader.getSheetFromFileByName(SAMPLE_XLSX_FILE_PATH, LocationReader.REPORT_DATA_SHEET_NAME);
        List<Location> locations = locationReader.getAllLocationFromSheet(sheet);
        int count = deviceService.countDeviceInCountry("Vietnam", locations);
        assertEquals("Device in country", 1, count);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCountDeviceNotFoundCountry() throws IOException, InvalidFormatException {
        Sheet sheet = locationReader.getSheetFromFileByName(SAMPLE_XLSX_FILE_PATH, LocationReader.REPORT_DATA_SHEET_NAME);
        List<Location> locations = locationReader.getAllLocationFromSheet(sheet);
        deviceService.countDeviceInCountry("ABC", locations);
    }
}
