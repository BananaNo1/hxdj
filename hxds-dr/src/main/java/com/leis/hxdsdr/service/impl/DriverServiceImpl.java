package com.leis.hxdsdr.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.common.util.MicroAppUtil;
import com.leis.hxdsdr.db.dao.DriverDao;
import com.leis.hxdsdr.db.dao.DriverSettingsDao;
import com.leis.hxdsdr.db.dao.WalletDao;
import com.leis.hxdsdr.db.pojo.DriverSettingsEntity;
import com.leis.hxdsdr.db.pojo.WalletEntity;
import com.leis.hxdsdr.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DriverServiceImpl implements DriverService {

    @Autowired
    private MicroAppUtil microAppUtil;

    @Resource
    private DriverDao driverDao;

    @Resource
    private DriverSettingsDao settingsDao;

    @Resource
    private WalletDao walletDao;


    @Override
    @Transactional
    @LcnTransaction
    public String registerNewDriver(Map param) {
        String code = MapUtil.getStr(param, "code");
        String openId = microAppUtil.getOpenId(code);

        HashMap tempParam = new HashMap<>() {{
            put("openId", openId);
        }};
        if (driverDao.hasDriver(tempParam) != 0) {
            throw new HxdsException("该微信无法注册");
        }
        param.put("openId", openId);
        driverDao.registerNewDriver(param);
        String driverId = driverDao.searchDriverId(openId);

        DriverSettingsEntity settingsEntity = new DriverSettingsEntity();
        settingsEntity.setDriverId(Long.parseLong(driverId));
        JSONObject json = new JSONObject();
        json.set("orientation", "");
        json.set("listenService", true);
        json.set("orderDistance", 0);
        json.set("rangeDistance", 5);
        json.set("autoAccept", false);
        settingsEntity.setSettings(json.toString());
        settingsDao.insertDriverSettings(settingsEntity);

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setDriverId(Long.parseLong(driverId));
        walletEntity.setBalance(new BigDecimal("0"));
        walletEntity.setPassword(null);
        walletDao.insert(walletEntity);
        return driverId;
    }
}
