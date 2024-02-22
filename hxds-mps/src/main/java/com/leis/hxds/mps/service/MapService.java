package com.leis.hxds.mps.service;

import java.util.HashMap;

public interface MapService {

    HashMap estimateOrderMileageAndMinute(String mode, String startPlaceLatitude, String startPlaceLongitude,
                                          String endPlaceLatitude, String endPlaceLongitude);


    HashMap calculateDriveLine(String startPlaceLatitude, String startPlaceLongitude, String endPlaceLatitude,
                               String endPlaceLongitude);
}
