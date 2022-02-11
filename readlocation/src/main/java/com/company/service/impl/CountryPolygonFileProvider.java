package com.company.service.impl;

import com.company.service.CountryPolygonService;
import com.company.utils.ResourceLoader;
import org.apache.commons.lang3.StringUtils;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CountryPolygonFileProvider implements CountryPolygonService {

    public static final String DATA_FOLDER = "data";
    public static final String WORLD_COUNTRIES_SHAPE_FILE_PATH = "World_Countries/World_Countries.shp";

    public Map<String, List<Coordinate>> fetchCountriesPolygon() throws IOException {
        String path = ResourceLoader.getResourcePath(DATA_FOLDER + ResourceLoader.SUFFIX + WORLD_COUNTRIES_SHAPE_FILE_PATH);
        if (path == null || path.isEmpty()) {
            return null;
        }
        File file = new File(path);
        Map<String, List<Coordinate>> map = new HashMap<>();

        Map<String, String> connect = new HashMap<>();
        connect.put("url", file.toURI().toString());
        DataStore dataStore = null;
        SimpleFeatureIterator iterator = null;

        try {
            dataStore = DataStoreFinder.getDataStore(connect);
            String[] typeNames = dataStore.getTypeNames();
            String typeName = typeNames[0];

            SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
            SimpleFeatureCollection collection = featureSource.getFeatures();
            iterator = collection.features();

            while (iterator.hasNext()) {
                List<Coordinate> coordinates = new ArrayList<>();
                SimpleFeature feature = iterator.next();
                String featureString = feature.toString();
                String country = feature.getAttribute("COUNTRY").toString();
                String polygonCoordinates = StringUtils.substringBetween(featureString, "(((", ")))");
                List<String> polygonList = Arrays.asList(polygonCoordinates.split(","));

                SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();
                b.setName(country);
                b.setCRS(DefaultGeographicCRS.WGS84);
                b.add("location", Point.class);

                int size = polygonList.size();
                for (int i = 0; i < size; i++) {
                    String axis = polygonList.get(i);
                    axis = axis.replace(')', ' ');
                    axis = axis.replace('(', ' ');
                    axis = axis.trim();
                    List<String> splitAxis = Arrays.asList(axis.split(" "));
                    if (splitAxis == null || splitAxis.size() < 2 || splitAxis.get(0).isEmpty() || splitAxis.get(1).isEmpty()) {
                        continue;
                    }
                    double lng = Double.parseDouble(splitAxis.get(0));
                    double lat = Double.parseDouble(splitAxis.get(1));
                    Coordinate coordinate = new Coordinate(lat, lng);
                    coordinates.add(coordinate);
                }
                map.put(country, coordinates);
            }

            return map;
        } finally {
            if (iterator != null){
                iterator.close();
            }
            if (dataStore != null){
                dataStore.dispose();
            }
        }
    }

}

