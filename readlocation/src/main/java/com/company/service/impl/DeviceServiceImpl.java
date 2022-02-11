package com.company.service.impl;

import com.company.model.Location;
import com.company.model.Polygon;
import com.company.service.CountryPolygonService;
import com.company.service.DeviceService;
import org.locationtech.jts.geom.Coordinate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DeviceServiceImpl implements DeviceService {

    CountryPolygonService countryPolygonService;

    public DeviceServiceImpl (CountryPolygonService countryPolygonService){
        this.countryPolygonService = countryPolygonService;
    }

    @Override
    public int countDeviceInCountry(String countryName, List<Location> locations) throws IOException {
        int count = 0;
        Map<String,List<Coordinate>> mapCountries = countryPolygonService.fetchCountriesPolygon();
        if (!mapCountries.containsKey(countryName)){
            throw new IllegalArgumentException("Country not found!");
        }
        List<Coordinate> coordinates = mapCountries.get(countryName);
        Polygon polygon = new Polygon();
        polygon.setCoordinates(coordinates);

        for (Location location : locations){
            if (polygon.isContain(location.getCoordinate())){
                count++;
            }
        }

        return count;
    }
}
