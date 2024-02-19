package com.leis.hxds.mis.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.leis.hxds.common.util.CosUtil;
import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.SearchDriverByPageForm;
import com.leis.hxds.mis.api.controller.form.SearchDriverRealSummaryForm;
import com.leis.hxds.mis.api.feign.DrServiceApi;
import com.leis.hxds.mis.api.service.DriverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DrServiceApi drServiceApi;

    @Resource
    private CosUtil cosUtil;

    @Override
    public PageUtils SearchDriverByPage(SearchDriverByPageForm form) {
        R r = drServiceApi.searchDriverByPage(form);
        HashMap map = (HashMap) r.get("result");
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }

    @Override
    public HashMap SearchDriverComprehensiveData(byte realAuth, Long driverId) {
        HashMap map = new HashMap();
        if (realAuth == 2 || realAuth == 3) {
            SearchDriverRealSummaryForm form_1 = new SearchDriverRealSummaryForm();
            form_1.setDriverId(driverId);
            R r = drServiceApi.searchDriverRealSummary(form_1);
            HashMap summaryMap = (HashMap) r.get("result");

            String idcardFront = MapUtil.getStr(summaryMap, "idcardFront");
            String idcardBack = MapUtil.getStr(summaryMap, "idcardBack");
            String idcardHolding = MapUtil.getStr(summaryMap, "idcardHolding");
            String drcardFront = MapUtil.getStr(summaryMap, "drcardFront");
            String drcardBack = MapUtil.getStr(summaryMap, "drcardBack");
            String drcardHolding = MapUtil.getStr(summaryMap, "drcardHolding");
            idcardFront = idcardFront.length() > 0 ? cosUtil.getPrivateFileUrl(idcardFront) : "";
            idcardBack = idcardBack.length() > 0 ? cosUtil.getPrivateFileUrl(idcardBack) : "";
            idcardHolding = idcardHolding.length() > 0 ? cosUtil.getPrivateFileUrl(idcardHolding) : "";
            drcardFront = drcardFront.length() > 0 ? cosUtil.getPrivateFileUrl(drcardFront) : "";
            drcardBack = drcardBack.length() > 0 ? cosUtil.getPrivateFileUrl(drcardBack) : "";
            drcardHolding = drcardHolding.length() > 0 ? cosUtil.getPrivateFileUrl(drcardHolding) : "";
            summaryMap.replace("idcardFront", idcardFront);
            summaryMap.replace("idcardBack", idcardBack);
            summaryMap.replace("idcardHolding", idcardHolding);
            summaryMap.replace("drcardFront", drcardFront);
            summaryMap.replace("drcardBack", drcardBack);
            summaryMap.replace("drcardHolding", drcardHolding);
            map.put("summaryMap", summaryMap);
            //todo
        }

        return map;
    }
}
