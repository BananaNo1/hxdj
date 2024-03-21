package com.leis.hxds.mis.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.AcceptCommentAppealForm;
import com.leis.hxds.mis.api.controller.form.HandleCommentAppealForm;
import com.leis.hxds.mis.api.controller.form.SearchAppealContentForm;
import com.leis.hxds.mis.api.controller.form.SearchCommentByPageForm;
import com.leis.hxds.mis.api.db.dao.UserDao;
import com.leis.hxds.mis.api.feign.OdrServiceApi;
import com.leis.hxds.mis.api.feign.WorkflowServiceApi;
import com.leis.hxds.mis.api.service.OrderCommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class OrderCommentServiceImpl implements OrderCommentService {

    @Resource
    private OdrServiceApi odrServiceApi;
    @Resource
    private WorkflowServiceApi workflowServiceApi;
    @Resource
    private UserDao userDao;

    @Override
    public PageUtils searchCommentByPage(SearchCommentByPageForm form) {
        R r = odrServiceApi.searchCommentByPage(form);
        HashMap map = (HashMap) r.get("result");
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }

    @Override
    @Transactional
    @LcnTransaction
    public void acceptCommentAppeal(AcceptCommentAppealForm form) {
        HashMap map = userDao.searchUserSummary(form.getUserId());
        String name = MapUtil.getStr(map, "name");
        form.setUserName(name);
        workflowServiceApi.acceptCommentAppeal(form);
    }

    @Override
    @Transactional
    @LcnTransaction
    public void handleCommentAppeal(HandleCommentAppealForm form) {
        workflowServiceApi.handleCommentAppeal(form);
    }

    @Override
    public HashMap searchAppealContent(SearchAppealContentForm form) {
        R r = workflowServiceApi.searchAppealContent(form);
        HashMap map = (HashMap) r.get("result");
        return map;
    }

}
