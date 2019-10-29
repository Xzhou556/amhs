package amhs.amhs.service.impl;

import amhs.amhs.dao.DepartmentDao;
import amhs.amhs.entity.Department;
import amhs.amhs.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentDao departmentDao;

    public Department replace(Department curr,Department origin){

        if (curr.getDepartmentName() == null){
            origin.setDepartmentName(curr.getDepartmentName());
        }
        if (curr.getLeader() == null){
            curr.setLeader(origin.getLeader());
        }
        if (curr.getPhone() == null){
            curr.setPhone(origin.getPhone());
        }
        if (curr.getTel() == null){
            curr.setTel(origin.getTel());
        }
        if (curr.getRemark() == null){
            curr.setRemark(origin.getRemark());
        }
        if (curr.getCreatetime() == null){
            curr.setCreatetime(origin.getCreatetime());
        }
        if (curr.getFactory() == null){
            curr.setFactory(origin.getFactory());
        }
        return curr;
    }


    @Override
    public Integer update(Department department) {
        Department origin = departmentDao.findId(department.getDepartmentId());
        department=replace(department,origin);
        departmentDao.save(department);
        return 1;
    }

    @Override
    public Page<Department> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum==null||pageNum==0){
            pageNum=1;
        }
        if (pageSize==null||pageSize==0){
            pageSize=15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<Department> all = departmentDao.findAll(of);
        return all;
    }
}
