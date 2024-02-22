package com.leis.hxds.mps.controller;


import com.leis.hxds.common.util.R;
import com.leis.hxds.mps.controller.form.CalculateDriveLineForm;
import com.leis.hxds.mps.controller.form.EstimateOrderMileageAndMinuteForm;
import com.leis.hxds.mps.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/map")
@Tag(name = "MapController", description = "地图接口")
public class MapController {

    @Resource
    private MapService mapService;

    @PostMapping("/estimateOrderMileageAndMinute")
    @Operation(summary = "估计里程和时间")
    public R estimateOrderMileageAndMinute(@RequestBody @Valid EstimateOrderMileageAndMinuteForm form) {
        HashMap map = mapService.estimateOrderMileageAndMinute(form.getMode(), form.getStartPlaceLatitude(),
                form.getStartPlaceLongitude(), form.getEndPlaceLatitude(), form.getEndPlaceLongitude());
        return R.ok().put("result", map);
    }

    @PostMapping("/calculateDriveLine")
    @Operation(summary = "计算行驶路线")
    public R calculateDriveLine(@RequestBody @Valid CalculateDriveLineForm form) {
        HashMap map = mapService.calculateDriveLine(form.getStartPlaceLatitude(), form.getStartPlaceLongitude(),
                form.getEndPlaceLatitude(), form.getEndPlaceLongitude());
        return R.ok().put("result", map);
    }
}
