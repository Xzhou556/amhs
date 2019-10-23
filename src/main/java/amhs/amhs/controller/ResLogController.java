package amhs.amhs.controller;


import amhs.amhs.dao.ResLogDao;
import amhs.amhs.entity.ResLog;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.ResLogService;
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
@RequestMapping("/resLog")
@RestController
@Api(value = "日志api", tags = "日志api")
public class ResLogController {
    private static final Logger LOG = Logger.getLogger(FactoryController.class);
    @Autowired
    ResLogDao resLogDao;
    @Autowired
    ResLogService resLogService;

    @GetMapping("/findAllResLog")
    @ApiOperation(value = "遍历所有日志", notes = "遍历所有日志")
    public RestResult findAllResLog(@RequestParam(value = "pageNum", required = false) Integer pageNum, @RequestParam(value = "pageSize", required = false) Integer pageSize){
        if (pageNum == null ||pageNum==0){
            pageNum =1;
        }
        Map<String,Object> map = new HashMap<>();
        Page<ResLog> all = resLogService.findAll(pageNum, pageSize);
        List<ResLog> content = all.getContent();
        long total = resLogDao.count();
        map.put("total", total);
        map.put("content", content);
        return new ResultGenerator().getSuccessResult(map);
    }


    @DeleteMapping("/deleteResLog")
    @ApiOperation(value = "删除日志", notes = "删除日志")
    public RestResult deleteResLog(@RequestParam(value = "ids") String ids) {
        if (ids == null) {
            return new ResultGenerator().getFailResult("数据不能为空");
        } else {
            String[] arr = ids.split(",");
            for (int i = 0; i < arr.length; i++) {
                resLogDao.deleteLogID(Integer.parseInt(arr[i]));
                LOG.info("日志删除成功");
            }
            return new ResultGenerator().getSuccessResult();
        }
    }
}
