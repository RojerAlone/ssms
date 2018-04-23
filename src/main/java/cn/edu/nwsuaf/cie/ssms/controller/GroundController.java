package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.GroundService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangrenjie on 2017-12-07
 */
@RestController
@RequestMapping("/ground")
public class GroundController extends AbstractController {

    @Autowired
    private GroundService groundService;

    @GetMapping("/all")
    public Result getAll(@RequestParam int type, @RequestParam String startTime, @RequestParam String endTime) {
        return groundService.getEmptyGround(type, startTime, endTime);
    }

    @GetMapping("/badminton")
    public Result getBadmintonInfo(@RequestParam(value = "date", required = false) String date) {
        return groundService.getBadmintonInfo(date);
    }

    @GetMapping("/gymnastics")
    public Result getGymnasticsInfo() {
        return groundService.getGymnasticsInfo();
    }

}
