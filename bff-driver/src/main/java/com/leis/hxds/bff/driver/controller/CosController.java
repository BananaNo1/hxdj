package com.leis.hxds.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckBasic;
import com.leis.hxds.bff.driver.controller.form.DeleteCosFileForm;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.common.util.CosUtil;
import com.leis.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/cos")
@Slf4j
@Tag(name = "CosController", description = "对象存储Web接口")
public class CosController {

    @Autowired
    private CosUtil cosUtil;

    @PostMapping("/uploadCosPublicFile")
    @SaCheckBasic
    @Operation(summary = "上传文件")
    public R uploadCosPublicFile(@Param("file") MultipartFile file, @Param("module") String module) {

        if (file.isEmpty()) {
            throw new HxdsException("上传文件不能为空");
        }
        try {
            String path = null;

            HashMap hashMap = cosUtil.uploadPrivateFile(file, path);
            return R.ok(hashMap);
        } catch (IOException e) {
            log.error("文件上传到腾讯云服务器", e);
            throw new HxdsException("文件上传到腾讯云服务器错误");
        }
    }

    @PostMapping("/uploadCosPrivateFile")
    @SaCheckBasic
    @Operation(summary = "上传文件")
    public R uploadCosPrivateFile(@Param("file") MultipartFile file, @Param("module") String module) {
        if (file.isEmpty()) {
            throw new HxdsException("上传文件不能为空");
        }
        try {
            String path = null;
            if ("driverAuth".equals(module)) {
                path = "/driver/auth/";
            } else {
                throw new HxdsException("module错误");
            }
            HashMap map = cosUtil.uploadPrivateFile(file, path);
            return R.ok(map);
        } catch (IOException e) {
            log.error("文件上传到腾讯云服务器", e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/deleteCosPublicFile")
    @SaCheckBasic
    @Operation(summary = "删除文件")
    public R deletePublicFile(@Valid @RequestBody DeleteCosFileForm form) {
        cosUtil.deletePublicFile(form.getPaths());
        return R.ok();
    }

    @PostMapping("/deleteCosPrivateFile")
    @SaCheckBasic
    @Operation(summary = "删除文件")
    public R deletePrivateFile(@Valid @RequestBody DeleteCosFileForm form) {
        cosUtil.deletePrivateFile(form.getPaths());
        return R.ok();
    }
}
