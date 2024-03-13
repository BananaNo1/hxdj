package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.form.InsertCommentForm;

public interface OrderCommentService {
    int insertComment(InsertCommentForm form);
}
