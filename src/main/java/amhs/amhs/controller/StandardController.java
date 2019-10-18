package amhs.amhs.controller;


import amhs.amhs.dao.StandardDao;
import amhs.amhs.entity.Standard;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.StandardService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RequestMapping("/standard")
@RestController
@Api(value = "数据标准api", tags = "数据标准api")
public class StandardController {
    private static final Logger LOG = Logger.getLogger(StandardController.class);
    @Autowired
    StandardDao standardDao;

    @Autowired
    StandardService standardService;

    @PostMapping("/addStandard")
    @ApiOperation(value = "添加标准", notes = "添加标准")
    public RestResult addStandard(@RequestBody String data) {
        JSONObject jsonObject = JSON.parseObject(data);


        return new ResultGenerator().getSuccessResult();

    }

    @PutMapping("/updateStandard")
    @ApiOperation(value = "修改标准", notes = "修改标准")
    public RestResult updateStandard(@RequestBody Standard standard) {
        if (standard.getStandardId() == null || "".equals(standard.getStandardId())) {
            LOG.error("数据不能为空");
        } else {
            standardService.update(standard);
        }
        return new ResultGenerator().getSuccessResult();
    }

    @DeleteMapping("/deleteStandard")
    @ApiOperation(value = "删除标准", notes = "删除标准")
    public RestResult deleteStandard(@RequestParam String ids) {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            try {
                standardDao.deleteByStandardId(Integer.parseInt(idsStr[i]));
            } catch (Exception e) {
                return new ResultGenerator().getFailResult("有角色在使用");
            }
        }
        return new ResultGenerator().getSuccessResult();
    }

    @GetMapping("/standardDetail")
    @ApiOperation(value = "查询单个传感器类型", notes = "查询单个传感器类型")
    public RestResult standardDetail(Integer id) {
        Standard standard = standardDao.findId(id);
        return new ResultGenerator().getSuccessResult(standard);
    }


}
