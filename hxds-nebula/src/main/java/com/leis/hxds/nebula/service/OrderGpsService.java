package com.leis.hxds.nebula.service;

import com.leis.hxds.nebula.controller.vo.InsertOrderGpsVo;

import java.util.ArrayList;

public interface OrderGpsService {
    int insertOrderGps(ArrayList<InsertOrderGpsVo> list);
}
