package com.leis.hxds.bff.driver.feign;

import com.leis.hxds.bff.driver.controller.form.CalculateIncentiveFeeForm;
import com.leis.hxds.bff.driver.controller.form.CalculateOrderChargeForm;
import com.leis.hxds.bff.driver.controller.form.CalculateProfitsharingForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-rule")
public interface RuleServiceApi {

    @PostMapping("/charge/calculateOrderCharge")
    R calculateOrderCharge(CalculateOrderChargeForm form);

    @PostMapping("/award/calculateIncentiveFee")
    R calculateIncentiveFee(CalculateIncentiveFeeForm form);


    @PostMapping("/profitsharing/calculateProfitsharing")
    R calculateProfitsharing(CalculateProfitsharingForm form);
}
