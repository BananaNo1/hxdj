package com.leis.hxds.mis.api.feign;

import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.SearchCommentByPageForm;
import com.leis.hxds.mis.api.controller.form.SearchOrderByPageForm;
import com.leis.hxds.mis.api.controller.form.SearchOrderContentForm;
import com.leis.hxds.mis.api.controller.form.SearchOrderStatusForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-odr")
public interface OdrServiceApi {

    @PostMapping("/order/searchOrderByPage")
    R searchOrderByPage(SearchOrderByPageForm form);

    @PostMapping("/order/searchOrderContent")
    R searchOrderContent(SearchOrderContentForm form);

    @PostMapping("/order/searchOrderStatus")
    R searchOrderStatus(SearchOrderStatusForm form);

    @PostMapping("/order/searchOrderStartLocationIn30Days")
    R searchOrderStartLocationIn30Days();

    @PostMapping("/comment/searchCommentByPage")
    R searchCommentByPage(SearchCommentByPageForm form);
}
