package com.leis.hxds.odr.service.impl;

import cn.hutool.core.map.MapUtil;
import com.leis.hxds.odr.db.dao.OrderDao;
import com.leis.hxds.odr.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Override
    public HashMap searchDriverTodayBusinessData(long driverId) {
        HashMap map = orderDao.searchDriverTodayBusinessData(driverId);
        String duration = MapUtil.getStr(map, "duration");
        if (duration == null) {
            duration = "0";
        }
        map.replace("duration", duration);
        String income = MapUtil.getStr(map, "income");
        if (income == null) {
            income = "0.00";
        }
        map.replace("income", income);
        return map;
    }
}
