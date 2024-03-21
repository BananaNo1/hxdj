package com.leis.hxds.mis.api.service;

import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.mis.api.controller.form.AcceptCommentAppealForm;
import com.leis.hxds.mis.api.controller.form.HandleCommentAppealForm;
import com.leis.hxds.mis.api.controller.form.SearchAppealContentForm;
import com.leis.hxds.mis.api.controller.form.SearchCommentByPageForm;

import java.util.HashMap;

public interface OrderCommentService {

    PageUtils searchCommentByPage(SearchCommentByPageForm form);

    void acceptCommentAppeal(AcceptCommentAppealForm form);

    void handleCommentAppeal(HandleCommentAppealForm form);

    HashMap searchAppealContent(SearchAppealContentForm form);

}
