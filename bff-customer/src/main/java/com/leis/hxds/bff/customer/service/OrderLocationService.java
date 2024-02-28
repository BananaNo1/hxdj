package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.form.SearchOrderLocationCacheForm;

import java.util.HashMap;

public interface OrderLocationService {
    HashMap searchOrderLocationCache(SearchOrderLocationCacheForm form);
}
