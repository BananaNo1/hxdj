package com.leis.hxds.bff.customer.feign;

import com.leis.hxds.bff.customer.controller.form.EstimateOrderMileageAndMinuteForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-mps")
public interface MpsServiceApi {

    @PostMapping("/map/estimateOrderMileageAndMinute")
    R estimateOrderMileageAndMinute(EstimateOrderMileageAndMinuteForm form);

}
