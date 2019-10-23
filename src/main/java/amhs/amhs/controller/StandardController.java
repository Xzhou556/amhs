package amhs.amhs.controller;


import amhs.amhs.dao.StandardDao;
import amhs.amhs.dao.TransducerTypeDao;
import amhs.amhs.entity.Standard;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.StandardService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    TransducerTypeDao transducerTypeDao;


    @PostMapping("/updateStandard")
    @ApiOperation(value = "修改标准", notes = "修改标准")
    public RestResult updateStandard(@RequestBody String data) {
        // System.out.println(data);
        Standard standard2 = null;
        JSONObject jsonObject = JSON.parseObject(data);
        Integer ttId = jsonObject.getInteger("ttId");
        String standard = jsonObject.getString("standard");
        JSONArray standardArr = JSONArray.parseArray(standard);
        for (int i = 0; i < standardArr.size(); i++) {
             standard2 = new Standard();
            JSONObject standard1 = standardArr.getJSONObject(i);
            Integer standardId = standard1.getInteger("standardId");
            String name = standard1.getString("name");
            BigDecimal lowerLimit = standard1.getBigDecimal("lowerlimit");
            BigDecimal upperLimit = standard1.getBigDecimal("upperlimit");
            String unit = standard1.getString("unit");
            standard2.setTransducerType(transducerTypeDao.findId(ttId));

            standard2.setUnit(unit);
            standard2.setLowerLimit(lowerLimit);
            standard2.setUpperLimit(upperLimit);
            standard2.setName(name);
            if (standardId == null) {
                standardDao.save(standard2);
                LOG.info("传感器标准添加成功");
            } else {
                standard2.setStandardId(standardId);
                standardService.update(standard2);
                LOG.info("传感器标准修改成功");
            }

        }

        return new ResultGenerator().getSuccessResult();

    }

    @DeleteMapping("/deleteStandard")
    @ApiOperation(value = "删除标准", notes = "删除标准")
    public RestResult deleteStandard(@RequestParam String ids) {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
                standardDao.deleteByStandardId(Integer.parseInt(idsStr[i]));
            LOG.info("传感器标准删除成功");
            }
        return new ResultGenerator().getSuccessResult();
    }

    @GetMapping("/standardDetail")
    @ApiOperation(value = "查询单个标准", notes = "查询单个标准")
    public RestResult standardDetail(Integer id) {
        Standard standard = standardDao.findId(id);
        return new ResultGenerator().getSuccessResult(standard);
    }

    @GetMapping("/transducerTypeList")
    @ApiOperation(value = "获取所有传感器类型", notes = "获取所有传感器类型")
    public RestResult transducerTypeList(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        if (pageNum == 0 || pageNum == null) {
            pageNum = 1;
        }
        Map<String, Object> map = new HashMap<>();
        Page<Standard> all = standardService.findAll(pageNum, pageSize);
        List<Standard> content = all.getContent();
        long total = standardDao.count();
        map.put("total", total);
        map.put("content", content);
        return new ResultGenerator().getSuccessResult(map);
    }

    @GetMapping("/transducerTypeListByTtId")
    @ApiOperation(value = "获取所有传感器类型", notes = "获取所有传感器类型")
    public RestResult transducerTypeListByTtId(@RequestParam(value = "id") Integer id) {

        Map<String, Object> map = new HashMap<>();
        List<Standard> standards = standardDao.findTtId(id);
        long total = standardDao.count();
        map.put("total", total);
        map.put("content", standards);
        return new ResultGenerator().getSuccessResult(map);
    }
}
