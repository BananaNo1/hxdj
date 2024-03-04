package com.leis.hxds.nebula.service.impl;

import com.leis.hxds.nebula.controller.vo.InsertOrderGpsVo;
import com.leis.hxds.nebula.db.dao.OrderGpsDao;
import com.leis.hxds.nebula.db.pojo.OrderGpsEntity;
import com.leis.hxds.nebula.service.OrderGpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class OrderGpsServiceImpl implements OrderGpsService {

    @Resource
    private OrderGpsDao orderGpsDao;

    @Override
    @Transactional
    public int insertOrderGps(ArrayList<InsertOrderGpsVo> list) {
        int rows = 0;
        for (OrderGpsEntity entity : list) {
            rows += orderGpsDao.insert(entity);
        }
        return rows;
    }

    @Override
    public ArrayList<HashMap> searchOrderGps(long orderId) {
        ArrayList<HashMap> list = orderGpsDao.searchOrderGps(orderId);
        return list;
    }

    @Override
    public HashMap searchOrderLastGps(long orderId) {
        HashMap map = orderGpsDao.searchOrderLastGps(orderId);
        return map;
    }
}
