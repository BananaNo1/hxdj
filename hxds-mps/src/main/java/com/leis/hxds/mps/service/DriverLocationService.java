package com.leis.hxds.mps.service;

import java.util.ArrayList;
import java.util.Map;

public interface DriverLocationService {
    void updateLocationCache(Map param);

    void removeLocationCache(long driverId);

    ArrayList searchBefittingDriverAboutOrder(double startPlaceLatitude, double startPlaceLongitude,
                                              double endPlaceLatitude, double endPlaceLongitude, double mileage);
}
