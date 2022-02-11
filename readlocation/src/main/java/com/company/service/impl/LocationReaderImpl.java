package com.company.service.impl;

import com.company.model.Location;
import com.company.service.LocationReader;
import com.company.utils.ResourceLoader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.locationtech.jts.geom.Coordinate;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LocationReaderImpl implements LocationReader {
    public static final String DATA_FOLDER = "data";

    public static final String NAME_CELL_NAME = "Name";
    public static final String SERIAL_CELL_NAME = "Serial";

    public static final String ASSET_TAGS_CELL_NAME = "Asset tags";
    public static final String TECHNICAL_PRODUCT_KEY_CELL_NAME = "Technical product key";
    public static final String ORGANIZATIONS_CELL_NAME = "Organizations";
    public static final String LAST_SEEN_ALIVE_CELL_NAME = "Last seen alive";
    public static final String ADDRESS_CELL_NAME = "Address";

    public static final String CURRENT_LATITUDE_CELL_NAME = "Current latitude";
    public static final String CURRENT_LONGITUDE_CELL_NAME = "Current longitude";

    @Override
    public List<Location> getAllLocationFromSheet(Sheet sheet){
        List<Location> locations = new ArrayList<>();
        if (sheet == null){
            return locations;
        }
        Map<String, Integer> colIndexMap = getMappingColumnIndex(sheet);
        int maxRow = sheet.getLastRowNum();
        for (int i = 1; i <= maxRow; i++) {
            Row row = sheet.getRow(i);
            if (row == null) { continue; }
            Location location = new Location();

            Cell nameCell = row.getCell(colIndexMap.get(NAME_CELL_NAME), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if(nameCell != null){
                location.setName(nameCell.getStringCellValue());
            }
            Cell serialCell = row.getCell(colIndexMap.get(SERIAL_CELL_NAME), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (serialCell != null){
                location.setSerial(serialCell.getStringCellValue());
            }

            Cell assetTagsCell = row.getCell(colIndexMap.get(ASSET_TAGS_CELL_NAME), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (assetTagsCell != null){
                location.setAssetTags(assetTagsCell.getStringCellValue());
            }
            Cell technicalProductKeyCell = row.getCell(colIndexMap.get(TECHNICAL_PRODUCT_KEY_CELL_NAME), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (technicalProductKeyCell != null){
                location.setTechnicalProductKey(technicalProductKeyCell.getStringCellValue());
            }
            Cell organizationsCell = row.getCell(colIndexMap.get(ORGANIZATIONS_CELL_NAME), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (organizationsCell != null){
                location.setOrganizations(organizationsCell.getStringCellValue());
            }

            Cell addressCell = row.getCell(colIndexMap.get(ADDRESS_CELL_NAME), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (addressCell != null){
                location.setAddress(addressCell.getStringCellValue());
            }

            Cell latCell = row.getCell(colIndexMap.get(CURRENT_LATITUDE_CELL_NAME), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell lngCell = row.getCell(colIndexMap.get(CURRENT_LONGITUDE_CELL_NAME), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (latCell != null && lngCell != null){
                location.setCoordinate(new Coordinate(latCell.getNumericCellValue(), lngCell.getNumericCellValue()));
            }
            locations.add(location);
        }
        return locations;
    }

    @Override
    public Sheet getSheetFromFileByName(String fileName, String sheetName) throws IOException, InvalidFormatException {
        String path = ResourceLoader.getResourcePath(DATA_FOLDER + ResourceLoader.SUFFIX + fileName);
        if (path == null || path.isEmpty()) {
            return null;
        }
        Workbook workbook = WorkbookFactory.create(new File(path));
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()){
            Sheet sheet = sheetIterator.next();
            if (sheetName.equals(sheet.getSheetName())){
                return sheet;
            }
        }
        return null;
    }

    private Map<String, Integer> getMappingColumnIndex(Sheet sheet) {
        Map<String, Integer> mappingCol = new HashMap<>();
        mappingCol.put(NAME_CELL_NAME, -1);
        mappingCol.put(SERIAL_CELL_NAME, -1);
        mappingCol.put(ASSET_TAGS_CELL_NAME, -1);
        mappingCol.put(TECHNICAL_PRODUCT_KEY_CELL_NAME, -1);
        mappingCol.put(ORGANIZATIONS_CELL_NAME, -1);
        mappingCol.put(LAST_SEEN_ALIVE_CELL_NAME, -1);
        mappingCol.put(ADDRESS_CELL_NAME, -1);
        mappingCol.put(CURRENT_LATITUDE_CELL_NAME, -1);
        mappingCol.put(CURRENT_LONGITUDE_CELL_NAME, -1);

        Row colRow = sheet.getRow(0);
        for (int colIndex = 0; colIndex < 10; colIndex++) {
            Cell cell = colRow.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                String colName = cell.getStringCellValue();
                if (mappingCol.get(colName) != null) {
                    mappingCol.put(colName, colIndex);
                }
            }
        }
        return mappingCol;
    }
}
