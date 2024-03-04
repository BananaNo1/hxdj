package com.leis.hxds.mis.api.service;

import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.mis.api.controller.form.SearchOrderByPageForm;

import java.util.HashMap;

public interface OrderService {

    PageUtils searchOrderByPage(SearchOrderByPageForm form);

    HashMap searchOrderComprehensiveInfo(long orderId);
}