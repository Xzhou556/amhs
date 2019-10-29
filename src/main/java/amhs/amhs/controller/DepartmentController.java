package amhs.amhs.controller;

import amhs.amhs.dao.DepartmentDao;
import amhs.amhs.entity.Department;
import amhs.amhs.entity.TransducerType;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.DepartmentService;
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
@RequestMapping("/department")
@RestController
@Api(value = "部门api", tags = "部门api")
public class DepartmentController {
    private static final Logger LOG = Logger.getLogger(DepartmentController.class);
    @Autowired
    DepartmentDao departmentDao;
    @Autowired
    DepartmentService departmentService;

    @PostMapping("/addDepartment")
    @ApiOperation(value = "添加传感器类型", notes = "添加传感器类型")
    public RestResult addDepartment(@RequestBody Department department) {
        if (department == null) {
            LOG.error("数据不能为空");
        } else {
            departmentDao.save(department);
            LOG.info("部门添加成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

    @DeleteMapping("/deleteDepartment")
    @ApiOperation(value = "删除传感器类型", notes = "删除传感器类型")
    public RestResult deleteDepartment(@RequestParam String ids) {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            departmentDao.deleteId(Integer.parseInt(idsStr[i]));
            LOG.info("部门删除成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

    @PostMapping("/updateDepartment")
    @ApiOperation(value = "修改传感器", notes = "修改传感器")
    public RestResult updateDepartment(@RequestBody Department department) {
        if (department == null) {
            LOG.error("数据不能为空");
        } else {
            departmentService.update(department);
            LOG.info("部门修改成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

    @GetMapping("/departmentList")
    @ApiOperation(value = "获取所有部门", notes = "获取所有部门")
    public RestResult departmentList(@RequestParam(value = "pageNum", required = false) Integer pageNum, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNum == 0 || pageNum == null) {
            pageNum = 1;
        }
        Map<String, Object> map = new HashMap<>();
        if (pageSize == 0 || pageSize == null) {
            List<Department> content = departmentDao.findAll();
            map.put("content", content);
        } else {
            Page<Department> all = departmentService.findAll(pageNum, pageSize);
            List<Department> content = all.getContent();
            long total = departmentDao.count();
            map.put("total", total);
            map.put("content", content);
        }
        return new ResultGenerator().getSuccessResult(map);
    }


    @GetMapping("/departmentDetail")
    @ApiOperation(value = "查询单个部门", notes = "查询单个部门")
    public RestResult departmentDetail(Integer id) {
        Map<String, Object> map = new HashMap<>();
        Department department = departmentDao.findId(id);
        map.put("department", department);
        return new ResultGenerator().getSuccessResult(map);
    }
}
