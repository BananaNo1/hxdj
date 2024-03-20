package com.leis.hxds.bff.driver.feign;

import com.leis.hxds.bff.driver.controller.form.StartCommentWorkflowForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-workflow")
public interface WorkflowServiceApi {

    @PostMapping("/comment/startCommentWorkflow")
    R startCommentWorkflow(StartCommentWorkflowForm form);
}
