package com.leis.hxds.nebula.service.impl;

import cn.hutool.core.util.IdUtil;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.nebula.db.dao.OrderVoiceTextDao;
import com.leis.hxds.nebula.db.pojo.OrderVoiceTextEntity;
import com.leis.hxds.nebula.service.MonitoringService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
@Slf4j
public class MonitoringServiceImpl implements MonitoringService {

    @Resource
    private OrderVoiceTextDao orderVoiceTextDao;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Override
    public void monitoring(MultipartFile file, String name, String text) {
        //上传minio
        try {
            MinioClient client = new MinioClient.Builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
            client.putObject(
                    PutObjectArgs.builder().bucket("hxds-record").object(name)
                            .stream(file.getInputStream(), -1, 20971502)
                            .contentType("audio/x-mpeg")
                            .build());
        } catch (Exception e) {
            log.error("上传代驾录音文件失败", e);
            throw new HxdsException("上传代驾录音文件失败");
        }

        OrderVoiceTextEntity entity = new OrderVoiceTextEntity();
        //文件名称
        String[] temp = name.substring(0, name.indexOf(".mp3")).split("-");
        long orderId = Long.parseLong(temp[0]);

        String uuid = IdUtil.simpleUUID();
        entity.setUuid(uuid);
        entity.setOrderId(orderId);
        entity.setRecordFile(name);
        entity.setText(text);
        //保存Hbase
        int rows = orderVoiceTextDao.insert(entity);
        if (rows != 1) {
            throw new HxdsException("保存录音文稿失败");
        }

        //todo 执行文稿内容审查
    }
}
