package com.leis.hxds.mis.api.feign;


import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.AcceptCommentAppealForm;
import com.leis.hxds.mis.api.controller.form.HandleCommentAppealForm;
import com.leis.hxds.mis.api.controller.form.SearchAppealContentForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-workflow")
public interface WorkflowServiceApi {

    @PostMapping("/comment/acceptCommentAppeal")
    R acceptCommentAppeal(AcceptCommentAppealForm form);

    @PostMapping("/comment/handleCommentAppeal")
    R handleCommentAppeal(HandleCommentAppealForm form);

    @PostMapping("/comment/searchAppealContent")
    R searchAppealContent(SearchAppealContentForm form);
}
