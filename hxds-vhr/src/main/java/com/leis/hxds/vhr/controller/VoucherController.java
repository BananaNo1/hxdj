package com.leis.hxds.vhr.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.common.util.R;
import com.leis.hxds.vhr.controller.form.*;
import com.leis.hxds.vhr.db.pojo.VoucherEntity;
import com.leis.hxds.vhr.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/voucher")
@Tag(name = "VoucherController", description = "代金券Web接口")
public class VoucherController {

    @Resource
    private VoucherService voucherService;

    @PostMapping("/searchVoucherByPage")
    @Operation(summary = "查询代金券分页记录")
    public R searchVoucherByPage(@RequestBody @Valid SearchVoucherByPageForm form) {
        Map param = BeanUtil.beanToMap(form);
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = voucherService.searchVoucherByPage(param);
        return R.ok().put("result", pageUtils);
    }

    @PostMapping("/insertVoucher")
    @Operation(summary = "添加代金券")
    public R insertVoucher(@RequestBody @Valid InsertVoucherForm form) {
        VoucherEntity entity = BeanUtil.toBean(form, VoucherEntity.class);
        String uuid = IdUtil.simpleUUID();
        entity.setUuid(uuid);
        int rows = voucherService.insert(entity);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/updateVoucherStatus")
    @Operation(summary = "更新代金券状态")
    public R updateVoucherStatus(@RequestBody @Valid UpdateVoucherStatusForm form) {
        Map param = BeanUtil.beanToMap(form);
        int rows = voucherService.updateVoucherStatus(param);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/deleteVoucherByIds")
    @Operation(summary = "删除代金券")
    public R deleteVoucherByIds(@RequestBody @Valid DeleteVoucherByIdsForm form) {
        int rows = voucherService.deleteVoucherByIds(form.getIds());
        return R.ok().put("rows", rows);
    }

    @PostMapping("/searchUnTakeVoucherByPage")
    @Operation(summary = "查询未领取代金券的分页")
    public R searchUnTakeVoucherByPage(@RequestBody @Valid SearchUnTakeVoucherByPageForm form) {
        Map param = BeanUtil.beanToMap(form);
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = voucherService.searchUnTakeVoucherByPage(param);
        return R.ok().put("result", pageUtils);
    }

    @PostMapping("/searchUnUseVoucherByPage")
    @Operation(summary = "查询未使用代金券的分页")
    public R searchUnUseVoucherByPage(@RequestBody @Valid SearchUnUseVoucherByPageForm form) {
        Map param = BeanUtil.beanToMap(form);
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = voucherService.searchUnUseVoucherByPage(param);
        return R.ok().put("result", pageUtils);
    }

    @PostMapping("/searchUsedVoucherByPage")
    @Operation(summary = "查询已使用代金券的分页")
    public R searchUsedVoucherByPage(@RequestBody @Valid SearchUsedVoucherByPageForm form) {
        Map param = BeanUtil.beanToMap(form);
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = voucherService.searchUsedVoucherByPage(param);
        return R.ok().put("result", pageUtils);
    }

    @PostMapping("/searchUnUseVoucherCount")
    @Operation(summary = "查询未使用代金券数量")
    public R searchUnUseVoucherCount(@RequestBody @Valid SearchUnUseVoucherCountForm form) {
        Map param = BeanUtil.beanToMap(form);
        long count = voucherService.searchUnUseVoucherCount(param);
        return R.ok().put("result", count);
    }

    @PostMapping("/takeVoucher")
    @Operation(summary = "领取代金券")
    public R takeVoucher(@RequestBody @Valid TakeVoucherForm form) {
        Map param = BeanUtil.beanToMap(form);
        boolean bool = voucherService.takeVoucher(param);
        return R.ok().put("result", bool);
    }

    @PostMapping("/searchBestUnUseVoucher")
    @Operation(summary = "查询未使用的最佳代金券")
    public R searchBestUnUseVoucher(@RequestBody @Valid SearchBestUnUseVoucherForm form) {
        Map param = BeanUtil.beanToMap(form);
        HashMap map = voucherService.searchBestUnUseVoucher(param);
        return R.ok().put("result", map);
    }
}
