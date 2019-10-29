package amhs.amhs.service.impl;

import amhs.amhs.dao.EmployeeDao;
import amhs.amhs.entity.Employee;
import amhs.amhs.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeDao employeeDao;

    public Employee replace(Employee curr, Employee origin) {

        if (curr.getName() == null) {
            origin.setName(curr.getName());
        }
        if (curr.getIdCard() == null) {
            curr.setIdCard(origin.getIdCard());
        }
        if (curr.getSex() == null) {
            curr.setSex(origin.getSex());
        }
        if (curr.getPhone() == null) {
            curr.setPhone(origin.getPhone());
        }
        if (curr.getDuty() == null) {
            curr.setDuty(origin.getDuty());
        }
        if (curr.getCreatetime() == null) {
            curr.setCreatetime(origin.getCreatetime());
        }
        if (curr.getJoinTime() == null) {
            curr.setJoinTime(origin.getJoinTime());
        }
        if (curr.getState() == null) {
            curr.setState(origin.getState());
        }
        if (curr.getRemark() == null) {
            curr.setRemark(origin.getRemark());
        }
        if (curr.getDepartment() == null) {
            curr.setDepartment(origin.getDepartment());
        }
        return curr;
    }

    @Override
    public Integer update(Employee employee) {
        Employee origin = employeeDao.findId(employee.getEmployeeId());
        employee = replace(employee, origin);
        employeeDao.save(employee);
        return 1;
    }

    @Override
    public Page<Employee> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<Employee> all = employeeDao.findAll(of);
        return all;
    }
}
