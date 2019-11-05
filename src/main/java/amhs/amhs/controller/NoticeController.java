package amhs.amhs.controller;

import amhs.amhs.dao.NoticeDao;
import amhs.amhs.entity.Employee;
import amhs.amhs.entity.Notice;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RequestMapping("/notice")
@RestController
@Api(value = "公告api", tags = "公告api")
public class NoticeController {
    private static final Logger LOG = Logger.getLogger(EmployeeController.class);
    @Autowired
    NoticeDao noticeDao;
    @Autowired
    NoticeService noticeService;

    @PostMapping("/addNotice")
    @ApiOperation(value = "添加公告", notes = "添加公告")
    public RestResult addNotice(@RequestBody Notice notice) {
        if (notice == null) {
            LOG.error("数据不能为空");
        } else {
            noticeDao.save(notice);
            LOG.info("公告添加成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

    @DeleteMapping("/deleteNotice")
    @ApiOperation(value = "删除公告", notes = "删除公告")
    public RestResult deleteNotice(@RequestParam String ids) {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            noticeDao.deleteId(Integer.parseInt(idsStr[i]));
            LOG.info("公告删除成功");

        }
        return new ResultGenerator().getSuccessResult();
    }

    @PostMapping("/updateNotice")
    @ApiOperation(value = "修改公告", notes = "修改公告")
    public RestResult updateNotice(@RequestBody Notice notice) {
        if (notice == null) {
            LOG.error("数据不能为空");
        } else {
            noticeService.update(notice);
            LOG.info("公告修改成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

    @GetMapping("/noticeList")
    @ApiOperation(value = "获取所有公告", notes = "获取所有公告")
    public RestResult employeeList(@RequestParam(value = "pageNum", required = false) Integer pageNum, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNum == 0 || pageNum == null) {
            pageNum = 1;
        }
        Map<String, Object> map = new HashMap<>();
        if (pageSize == 0 || pageSize == null) {
            List<Notice> content = noticeDao.findAll();
            map.put("content", content);
        } else {
            Page<Notice> all = noticeService.findAll(pageNum, pageSize);
            List<Notice> content = all.getContent();
            long total = noticeDao.count();
            map.put("total", total);
            map.put("content", content);
        }
        return new ResultGenerator().getSuccessResult(map);
    }


    @GetMapping("/noticeDetail")
    @ApiOperation(value = "查询单个公告", notes = "查询单个公告")
    public RestResult employeeDetail(@RequestParam Integer id) {
        Map<String, Object> map = new HashMap<>();
        Notice notice = noticeDao.findId(id);
        map.put("notice", notice);
        return new ResultGenerator().getSuccessResult(map);
    }



}
