package com.leis.hxds.mis.api.feign;


import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.SearchCancelRuleByIdForm;
import com.leis.hxds.mis.api.controller.form.SearchChargeRuleByIdForm;
import com.leis.hxds.mis.api.controller.form.SearchProfitsharingRuleByIdForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-rule")
public interface RuleServiceApi {

    @PostMapping("/charge/searchChargeRuleById")
    R searchChargeRuleById(SearchChargeRuleByIdForm form);
    
    @PostMapping("/charge/searchCancelRuleById")
    R searchCancelRuleById(SearchCancelRuleByIdForm form);

    @PostMapping("/charge/searchProfitsharingRuleById")
    R searchProfitsharingRuleById(SearchProfitsharingRuleByIdForm form);
}
