package amhs.amhs.controller;


import amhs.amhs.dao.EmployeeDao;
import amhs.amhs.entity.Department;
import amhs.amhs.entity.Employee;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.EmployeeService;
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
@RequestMapping("/employee")
@RestController
@Api(value = "部门员工api", tags = "部门员工api")
public class EmployeeController {
    private static final Logger LOG = Logger.getLogger(EmployeeController.class);
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    EmployeeService employeeService;


    @PostMapping("/addEmployee")
    @ApiOperation(value = "添加部门员工", notes = "添加部门员工")
    public RestResult addEmployee(@RequestBody Employee employee) {
        if (employee == null) {
            LOG.error("数据不能为空");
        } else {
            String name = employee.getName().trim();
            employee.setName(name);
            employee.setCreatetime(new Date());
            employeeDao.save(employee);
            LOG.info("部门员工添加成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

    @DeleteMapping("/deleteEmployee")
    @ApiOperation(value = "删除部门员工", notes = "删除部门员工")
    public RestResult deleteEmployee(@RequestParam String ids) {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            employeeDao.deleteId(Integer.parseInt(idsStr[i]));
            LOG.info("部门员工删除成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

    @PostMapping("/updateEmployee")
    @ApiOperation(value = "修改部门员工", notes = "修改部门员工")
    public RestResult updateEmployee(@RequestBody Employee employee) {
        if (employee == null) {
            LOG.error("数据不能为空");
        } else {
            employeeService.update(employee);
            LOG.info("部门员工修改成功");
        }
        return new ResultGenerator().getSuccessResult();
    }

    @GetMapping("/employeeList")
    @ApiOperation(value = "获取所有部门员工", notes = "获取所有部门员工")
    public RestResult employeeList(@RequestParam(value = "pageNum", required = false) Integer pageNum, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNum == 0 || pageNum == null) {
            pageNum = 1;
        }
        Map<String, Object> map = new HashMap<>();
        if (pageSize == 0 || pageSize == null) {
            List<Employee> content = employeeDao.findAll();
            map.put("content", content);
        } else {
            Page<Employee> all = employeeService.findAll(pageNum, pageSize);
            List<Employee> content = all.getContent();
            long total = employeeDao.count();
            map.put("total", total);
            map.put("content", content);
        }
        return new ResultGenerator().getSuccessResult(map);
    }


    @GetMapping("/employeeDetail")
    @ApiOperation(value = "查询单个部门员工", notes = "查询单个部门员工")
    public RestResult employeeDetail(Integer id) {
        Map<String, Object> map = new HashMap<>();
        Employee employee = employeeDao.findId(id);
        map.put("employee", employee);
        return new ResultGenerator().getSuccessResult(map);
    }

}
