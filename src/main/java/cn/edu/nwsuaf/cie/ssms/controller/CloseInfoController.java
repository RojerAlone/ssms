package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.service.CloseInfoService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by RojerAlone on 2017-12-05.
 */
@RestController
@RequestMapping("/closeinfo")
public class CloseInfoController extends AbstractController {

    @Autowired
    private CloseInfoService closeInfoService;

    @PutMapping("{ground}")
    public Result insert(@PathVariable("ground") int gid, @RequestParam("date") Date closeDate,
                         @RequestParam(value = "startTime", required = false) Date startTime,
                         @RequestParam(value = "endDate", required = false) Date endDate,
                         @RequestParam(value = "endTime", required = false) Date endTime,
                         @RequestParam(value = "reason", required = false) String reason) {
        CloseInfo closeInfo = new CloseInfo();
        closeInfo.setGid(gid);
        closeInfo.setStartDate(closeDate);
        closeInfo.setStartTime(startTime);
        closeInfo.setEndDate(endDate);
        closeInfo.setEndTime(endTime);
        closeInfo.setReason(reason);
        return closeInfoService.close(closeInfo);
    }

    @DeleteMapping("{ground}")
    public Result delete(@PathVariable("ground") int gid) {
        return closeInfoService.delete(gid);
    }

}
