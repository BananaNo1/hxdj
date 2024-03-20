package com.leis.hxds.mis.api.service;

import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.mis.api.controller.form.AcceptCommentAppealForm;
import com.leis.hxds.mis.api.controller.form.SearchCommentByPageForm;

public interface OrderCommentService {

    PageUtils searchCommentByPage(SearchCommentByPageForm form);

    void acceptCommentAppeal(AcceptCommentAppealForm form);
}
