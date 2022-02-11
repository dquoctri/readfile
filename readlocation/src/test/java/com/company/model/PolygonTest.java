package com.company.model;

import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class PolygonTest {

    @Test
    public void testLocationInPolygon()  {
        Polygon polygon = createPolygon();
        Location location = new Location("11TUKU", "11TUKU", new Coordinate(50.04826, 2.93393));
        assertTrue("Check Polygon is contain location", polygon.isContain(location.getCoordinate()));
    }

    @Test
    public void testLocationIsNotInPolygon()  {
        Polygon polygon = createPolygon();
        Location location = new Location("Vietnam", "HCM", new Coordinate(100, 35));
        assertFalse("Check Polygon is not contain location", polygon.isContain(location.getCoordinate()));
    }

    private Polygon createPolygon(){
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(50.280526, 2.774703));
        coordinates.add(new Coordinate(50.134195, 3.545539));
        coordinates.add(new Coordinate(49.774842, 3.251576));
        coordinates.add(new Coordinate(49.884929, 2.574237));
        Polygon polygon = new Polygon();
        polygon.setCoordinates(coordinates);
        return polygon;
    }
}
