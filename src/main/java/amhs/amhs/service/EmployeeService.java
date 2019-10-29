package amhs.amhs.service;

import amhs.amhs.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    Integer update(Employee employee);

    Page<Employee> findAll(Integer pageNum, Integer pageSize);
}
