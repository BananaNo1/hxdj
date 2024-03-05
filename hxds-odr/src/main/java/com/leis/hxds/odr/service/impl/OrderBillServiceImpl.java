package com.leis.hxds.odr.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.odr.db.dao.OrderBillDao;
import com.leis.hxds.odr.db.dao.OrderDao;
import com.leis.hxds.odr.db.dao.OrderProfitsharingDao;
import com.leis.hxds.odr.db.pojo.OrderProfitsharingEntity;
import com.leis.hxds.odr.service.OrderBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
public class OrderBillServiceImpl implements OrderBillService {

    private OrderProfitsharingDao orderProfitsharingDao;

    @Resource
    private OrderBillDao orderBillDao;

    @Resource
    private OrderDao orderDao;

    @Override
    @Transactional
    @LcnTransaction
    public int updateBillFee(Map param) {
        //更新账单数据
        int rows = orderBillDao.updateBillFee(param);
        if (rows != 1) {
            throw new HxdsException("更新账单费用详情失败");
        }

        //更新订单数据
        rows = orderDao.updateOrderMileageAndFee(param);
        if (rows != 1) {
            throw new HxdsException("更新订单费用详情失败");
        }

        //添加分账单数据
        OrderProfitsharingEntity entity = new OrderProfitsharingEntity();
        entity.setOrderId(MapUtil.getLong(param, "orderId"));
        entity.setRuleId(MapUtil.getLong(param, "ruleId"));
        entity.setAmountFee(new BigDecimal((String) param.get("total")));
        entity.setPaymentFee(new BigDecimal((String) param.get("paymentFee")));
        entity.setPaymentRate(new BigDecimal((String) param.get("paymentRate")));
        entity.setTaxRate(new BigDecimal((String) param.get("taxRate")));
        entity.setTaxFee(new BigDecimal((String) param.get("taxFee")));
        entity.setSystemIncome(new BigDecimal((String) param.get("systemIncome")));
        entity.setDriverIncome(new BigDecimal((String) param.get("driverIncome")));
        rows = orderProfitsharingDao.insert(entity);
        if (rows != 1) {
            throw new HxdsException("添加分账记录失败");
        }
        return rows;
    }
}
