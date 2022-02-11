package com.company.service;

import org.locationtech.jts.geom.Coordinate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CountryPolygonService {
    Map<String,List<Coordinate>> fetchCountriesPolygon() throws IOException;
}
