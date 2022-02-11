package com.company;

import com.company.service.CountryPolygonService;
import com.company.service.DeviceService;
import com.company.service.LocationReader;
import com.company.service.impl.DeviceServiceImpl;
import com.company.service.impl.LocationReaderImpl;
import com.company.model.Location;
import com.company.service.impl.CountryPolygonFileProvider;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.locationtech.jts.geom.Coordinate;

import java.io.IOException;
import java.util.*;

public class Main {

    public static final String SAMPLE_XLSX_FILE_PATH = "location_data_2022_02_10.xlsx";

    public static void main(String[] args) throws IOException, InvalidFormatException {
        LocationReader locationReader = new LocationReaderImpl();
        Sheet sheet = locationReader.getSheetFromFileByName(SAMPLE_XLSX_FILE_PATH, LocationReader.REPORT_DATA_SHEET_NAME);
        List<Location> locations = locationReader.getAllLocationFromSheet(sheet);

        CountryPolygonService countryPolygonService = new CountryPolygonFileProvider();
        Map<String, List<Coordinate>> mapCountries = countryPolygonService.fetchCountriesPolygon();
        for (Map.Entry<String, List<Coordinate>> entry : mapCountries.entrySet()){
            System.out.println("Country *" + entry.getKey() + "* points count: " + entry.getValue().size());
        }

        DeviceService deviceService = new DeviceServiceImpl(new CountryPolygonFileProvider());
        int count = deviceService.countDeviceInCountry("Vietnam", locations);
        int countDeviceInBelgium = deviceService.countDeviceInCountry("Belgium", locations);
        System.out.println("Number of devices in Country Vietnam is " + count);
        System.out.println("Number of devices in Country Belgium is " + countDeviceInBelgium);
    }
}

