package com.company.service;

import com.company.model.Location;

import java.io.IOException;
import java.util.List;

public interface DeviceService {
    int countDeviceInCountry(String countryName, List<Location> locations) throws IOException;
}
