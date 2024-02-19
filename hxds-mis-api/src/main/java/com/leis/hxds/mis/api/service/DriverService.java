package com.leis.hxds.mis.api.service;

import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.mis.api.controller.form.SearchDriverByPageForm;

import java.util.HashMap;

public interface DriverService {

    PageUtils SearchDriverByPage(SearchDriverByPageForm form);

    HashMap SearchDriverComprehensiveData(byte realAuth,Long driverId);
}
