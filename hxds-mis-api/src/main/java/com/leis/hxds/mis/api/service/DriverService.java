package com.leis.hxds.mis.api.service;

import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.mis.api.controller.form.SearchDriverByPageForm;
import com.leis.hxds.mis.api.controller.form.UpdateDriverRealAuthForm;

import java.util.HashMap;

public interface DriverService {

    PageUtils searchDriverByPage(SearchDriverByPageForm form);

    HashMap searchDriverComprehensiveData(byte realAuth,Long driverId);

    int updateDriverRealAuth(UpdateDriverRealAuthForm form);
}
