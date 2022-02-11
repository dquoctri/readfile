package com.company.model;

import org.locationtech.jts.geom.Coordinate;

import java.awt.geom.Path2D;
import java.util.List;

public class Polygon {
    private List<Coordinate> coordinates;
    private Path2D path2d;

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
        this.path2d = new Path2D.Double();
        path2d.moveTo(coordinates.get(0).getX(), coordinates.get(0).getY());

        for (int i = 1; i < coordinates.size(); i++) {
            path2d.lineTo(coordinates.get(i).getX(), coordinates.get(i).getY());
        }
    }

    public boolean isContain(Coordinate coordinate) {
        if (3 > coordinates.size()) {
            return false;
        }
        return path2d.contains(coordinate.getX(), coordinate.getY());
    }
}
