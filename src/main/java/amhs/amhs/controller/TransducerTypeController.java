package amhs.amhs.controller;

import amhs.amhs.dao.TransducerTypeDao;
import amhs.amhs.entity.TransducerType;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.TransducerTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/transducerType")
@RestController
@Api(value = "传感器类型api", tags = "传感器类型api")
public class TransducerTypeController {
    private static final Logger LOG = Logger.getLogger(StandardController.class);
    @Autowired
    TransducerTypeDao transducerTypeDao;
    @Autowired
    TransducerTypeService transducerTypeService;

    @PostMapping("/addTransducerType")
    @ApiOperation(value = "添加传感器类型", notes = "添加传感器类型")
    public RestResult addTransducerType(@RequestBody TransducerType transducerType) {
        if (transducerType.getTtId() == null || "".equals(transducerType.getTtId())) {
            LOG.error("数据不能为空");
        } else {
            transducerTypeDao.save(transducerType);

        }
        return new ResultGenerator().getSuccessResult();
    }

    @DeleteMapping("/deleteTransducerType")
    @ApiOperation(value = "删除传感器类型", notes = "删除传感器类型")
    public RestResult deleteStandard(@RequestParam String ids) {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            try {
                transducerTypeDao.deleteId(Integer.parseInt(idsStr[i]));
            } catch (Exception e) {
                return new ResultGenerator().getFailResult("有角色在使用");
            }
        }
        return new ResultGenerator().getSuccessResult();
    }

    @PutMapping("/updateTransducerType")
    @ApiOperation(value = "修改传感器类型", notes = "修改传感器类型")
    public RestResult updateTransducerType(@RequestBody TransducerType transducerType) {
        if (transducerType.getTtId() == null || "".equals(transducerType.getTtId())) {
            LOG.error("数据不能为空");
        } else {
            transducerTypeService.update(transducerType);
        }
        return new ResultGenerator().getSuccessResult();
    }

    @GetMapping("/transducerTypeList")
    @ApiOperation(value = "获取所有传感器类型", notes = "获取所有传感器类型")
    public RestResult transducerTypeList(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        if (pageNum == 0 || pageNum == null) {
            pageNum = 1;
        }
        Map<String, Object> map = new HashMap<>();
        Page<TransducerType> all = transducerTypeService.findAll(pageNum, pageSize);
        List<TransducerType> content = all.getContent();
        long total = transducerTypeDao.count();
        map.put("total", total);
        map.put("content", content);
        return new ResultGenerator().getSuccessResult(map);
    }

    @GetMapping("/transducerTypeDetail")
    @ApiOperation(value = "查询单个传感器类型", notes = "查询单个传感器类型")
    public RestResult transducerTypeDetail(Integer id) {
        TransducerType transducerType = transducerTypeDao.findId(id);
        return new ResultGenerator().getSuccessResult(transducerType);
    }
}
