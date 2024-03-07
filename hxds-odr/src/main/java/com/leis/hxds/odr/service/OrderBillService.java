package com.leis.hxds.odr.service;

import java.util.HashMap;
import java.util.Map;

public interface OrderBillService {

    int updateBillFee(Map param);

    HashMap searchReviewDriverOrderBill(Map param);
}
