package amhs.amhs.service;

import amhs.amhs.entity.Department;
import org.springframework.data.domain.Page;

public interface DepartmentService {
    Integer update(Department department);

    Page<Department> findAll(Integer pageNum, Integer pageSize);
}
