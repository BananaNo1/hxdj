package com.leis.hxds.nebula.service;

import com.leis.hxds.nebula.controller.vo.InsertOrderGpsVo;

import java.util.ArrayList;
import java.util.HashMap;

public interface OrderGpsService {
    int insertOrderGps(ArrayList<InsertOrderGpsVo> list);

    ArrayList<HashMap> searchOrderGps(long orderId);

    HashMap searchOrderLastGps(long orderId);
}
