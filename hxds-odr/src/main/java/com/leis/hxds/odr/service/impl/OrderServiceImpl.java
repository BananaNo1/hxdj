package com.leis.hxds.odr.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.odr.db.dao.OrderBillDao;
import com.leis.hxds.odr.db.dao.OrderDao;
import com.leis.hxds.odr.db.pojo.OrderBillEntity;
import com.leis.hxds.odr.db.pojo.OrderEntity;
import com.leis.hxds.odr.service.OrderService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderBillDao orderBillDao;

    @Resource
    private RedisTemplate redisTemplate;

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

    @Override
    @Transactional
    @LcnTransaction
    public String insertOrder(OrderEntity orderEntity, OrderBillEntity orderBillEntity) {
        int rows = orderDao.insert(orderEntity);
        if(rows==1){
            String id = orderDao.searchOrderIdByUUID(orderEntity.getUuid());
            orderBillEntity.setOrderId(Long.parseLong(id));
            rows = orderBillDao.insert(orderBillEntity);
            if(rows==1){
                redisTemplate.opsForValue().set("order#"+id,"none");
                redisTemplate.expire("order#"+id,15, TimeUnit.MINUTES);
                return id;
            }else {
                throw new HxdsException("保存新订单费用失败");
            }
        }else {
            throw new HxdsException("保存新订单失败");
        }
    }
}
