package amhs.amhs.controller;

import amhs.amhs.dao.TransducerDao;
import amhs.amhs.entity.Factory;
import amhs.amhs.entity.Transducer;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.FactoryService;
import amhs.amhs.service.TransducerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RequestMapping("/transducer")
@RestController
@Api(value = "传感器api", tags = "传感器api")
public class TransducerController {
    private static final Logger LOG = Logger.getLogger(TransducerController.class);
    @Autowired
    FactoryService factoryService;

    @Autowired
    TransducerService transducerService;

    @Autowired
    TransducerDao transducerDao;


    @GetMapping("/transducerList")
    @ApiOperation(value = "获取所有传感器信息", notes = "遍历并分页")
    public RestResult transducerList(Integer pageNum, Integer pageSize) {
        if (pageNum == 0 || pageNum == null) {
            pageNum = 1;
        }
        Map<String, Object> map = new HashMap<>();
        Page<Transducer> transducers = transducerService.findT(pageNum, pageSize);
        List<Transducer> content = transducers.getContent();

        long total = transducerDao.count();
        map.put("content", content);
        map.put("total", total);
        if (map == null) {
            LOG.error("数据为空！！！");
        }
        return new ResultGenerator().getSuccessResult(map);

    }

    @GetMapping("/transducerDetail")
    @ApiOperation(value = "查询单个传感器", notes = "查询单个传感器")
    public RestResult transducerDetail(@RequestParam(value = "id", required = false) Integer id) {
        if (id == null) {
            LOG.error("数据为空！！！");
        }
        Transducer transducer = transducerDao.findId(id);
        return new ResultGenerator().getSuccessResult(transducer);
    }

    @DeleteMapping("/deleteTransducer")
    @ApiOperation(value = "删除传感器", notes = "删除传感器")
    public RestResult deleteTransducer(@RequestParam String ids) {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            transducerDao.deleteById(Integer.parseInt(idsStr[i]));
            LOG.info("传感器删除成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

    @PostMapping("/updateTransducer")
    @ApiOperation(value = "修改传感器", notes = "修改传感器")
    public RestResult updateFactory(@RequestBody Transducer transducer) {
        if (transducer == null) {
            LOG.error("数据不能为空");
        } else {
            transducerService.update(transducer);
            LOG.info("传感器修改成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

    @PostMapping("/addTransducer")
    @ApiOperation(value = "保存传感器", notes = "保存传感器")
    public RestResult addTransducer(@RequestBody Transducer transducer) {
        if (transducer == null) {
            LOG.error("数据不能为空");
        } else {
            transducer.setCreateDateTime(new Date());
            transducerDao.save(transducer);
            LOG.info("传感器添加成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

}
