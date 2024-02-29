package com.leis.hxds.nebula.controller;

import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.common.util.R;
import com.leis.hxds.nebula.service.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/monitoring")
@Tag(name = "MonitoringController", description = "监控服务的web接口")
@Slf4j
public class MonitoringController {

    @Resource
    private MonitoringService monitoringService;

    @PostMapping(value = "/updateRecordFile")
    @Operation(summary = "上传代驾录音文件")
    public R updateRecordFile(@RequestPart("file") MultipartFile file, @RequestPart("name") String name,
                              @RequestPart(value = "text", required = false) String text) {
        if (file.isEmpty()) {
            throw new HxdsException("录音文件不能为空");
        }
        monitoringService.monitoring(file, name, text);
        return R.ok();
    }
}
