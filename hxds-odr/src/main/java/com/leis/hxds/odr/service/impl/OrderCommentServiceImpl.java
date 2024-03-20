package com.leis.hxds.odr.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.odr.db.dao.OrderCommentDao;
import com.leis.hxds.odr.db.dao.OrderDao;
import com.leis.hxds.odr.db.pojo.OrderCommentEntity;
import com.leis.hxds.odr.service.OrderCommentService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.model.ciModel.auditing.AuditingJobsDetail;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingResponse;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderCommentServiceImpl implements OrderCommentService {

    @Value("${tencent.cloud.appId}")
    private String appId;
    @Value("${tencent.cloud.secretId}")
    private String secretId;
    @Value("${tencent.cloud.secretKey}")
    private String secretKey;
    @Value("${tencent.cloud.bucket-public}")
    private String bucketPublic;
    @Resource
    private OrderCommentDao orderCommentDao;
    @Resource
    private OrderDao orderDao;

    @Override
    @Transactional
    @LcnTransaction
    public int insert(OrderCommentEntity entity) {
        //验证司机和乘客与该订单是否有关联
        HashMap param = new HashMap<>() {{
            put("orderId", entity.getOrderId());
            put("driverId", entity.getDriverId());
            put("customerId", entity.getCustomerId());
        }};
        long count = orderDao.validDriverAndCustomerOwnOrder(param);
        if (count != 1) {
            throw new HxdsException("司机和乘客与该订单无关联");
        }
        //审核评价内容
        BasicCOSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-guangzhou");
        ClientConfig config = new ClientConfig(region);
        COSClient client = new COSClient(cred, config);
        TextAuditingRequest request = new TextAuditingRequest();
        request.setBucketName(bucketPublic);
        request.getInput().setContent(Base64.encode(entity.getRemark()));
        request.getConf().setDetectType("all");

        TextAuditingResponse response = client.createAuditingTextJobs(request);
        AuditingJobsDetail jobsDetail = response.getJobsDetail();
        String state = jobsDetail.getState();
        if ("SUCCESS".equals(state)) {
            String result = jobsDetail.getResult();
            //内容审查不同过就设置评价内容为nu11
            if (!"0".equals(result)) {
                entity.setRemark(null);
            }
        }
        //保存评价
        int rows = orderCommentDao.insert(entity);
        if (rows != 1) {
            throw new HxdsException("保存订单评价失败");
        }
        return rows;
    }

    @Override
    public HashMap searchCommentByOrderId(Map param) {
        HashMap map = orderCommentDao.searchCommentByOrderId(param);
        return map;
    }

    @Override
    public PageUtils searchCommentByPage(Map param) {
        long count = orderCommentDao.searchCommentCount(param);
        ArrayList<HashMap> list = null;
        if (count > 0) {
            list = orderCommentDao.searchCommentByPage(param);
            list.forEach(one -> {
                Integer temp = MapUtil.getInt(one, "handler");
                one.replace("handler", temp == 1);
            });
        } else {
            list = new ArrayList<>();
        }
        int start = MapUtil.getInt(param, "start");
        int length = MapUtil.getInt(param, "length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }
}
