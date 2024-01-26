package com.leis.hxdsdr.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.common.util.MicroAppUtil;
import com.leis.hxdsdr.db.dao.DriverDao;
import com.leis.hxdsdr.db.dao.DriverSettingsDao;
import com.leis.hxdsdr.db.dao.WalletDao;
import com.leis.hxdsdr.db.pojo.DriverSettingsEntity;
import com.leis.hxdsdr.db.pojo.WalletEntity;
import com.leis.hxdsdr.service.DriverService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20180301.IaiClient;
import com.tencentcloudapi.iai.v20180301.models.CreatePersonRequest;
import com.tencentcloudapi.iai.v20180301.models.CreatePersonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DriverServiceImpl implements DriverService {

    @Value("${tencent.cloud.secreId}")
    private String secretId;
    @Value("${tecent.cloud.secretKey}")
    private String secretKey;
    @Value("${tecent.cloud.face.groupName}")
    private String groupName;
    @Value("${tecent.cloud.face.region}")
    private String region;
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

    @Override
    @Transactional
    @LcnTransaction
    public int updateDriverAuth(Map param) {
        int rows = driverDao.updateDriverAuth(param);
        return rows;
    }

    @Override
    @Transactional
    @LcnTransaction
    public String createDriverFaceModel(long driverId, String photo) {
        HashMap hashMap = driverDao.searchDriverNameAndSex(driverId);
        String name = MapUtil.getStr(hashMap, "name");
        String sex = MapUtil.getStr(hashMap, "sex");

        Credential credential = new Credential(secretId, secretKey);
        IaiClient iaiClient = new IaiClient(credential, region);

        try {
            CreatePersonRequest createPersonRequest = new CreatePersonRequest();
            createPersonRequest.setGroupId(groupName);
            createPersonRequest.setPersonId(driverId + "");
            long gender = sex.equals("男") ? 1L : 2L;
            createPersonRequest.setPersonName(name);
            createPersonRequest.setGender(gender);
            createPersonRequest.setQualityControl(4L);
            createPersonRequest.setUniquePersonControl(4L);
            createPersonRequest.setImage(photo);
            CreatePersonResponse response = iaiClient.CreatePerson(createPersonRequest);
            if (StrUtil.isNotBlank(response.getFaceId())) {
                int rows = driverDao.updateDriverArchive(driverId);
                if (rows != 1) {
                    return "更新司机归档字段失败";
                }
            }
        } catch (TencentCloudSDKException e) {
            log.error("创建腾讯云端司机档案失败", e);
            return "创建腾讯云端司机档案失败";
        }
        return "";
    }

    @Override
    public HashMap login(String code) {
        String openId = microAppUtil.getOpenId(code);
        HashMap result = driverDao.login(openId);
        if (result != null && result.containsKey("archive")) {
            int temp = MapUtil.getInt(result, "archive");
            boolean archive = temp == 1 ? true : false;
            result.replace("archive", archive);
        }
        return result;
    }

    @Override
    public HashMap searchDriverBaseInfo(long driverId) {
        HashMap result = driverDao.searchDriverBaseInfo(driverId);
        JSONObject summary = JSONUtil.parseObj(MapUtil.getStr(result, "summary"));
        result.replace("summary", summary);
        return result;
    }
}
