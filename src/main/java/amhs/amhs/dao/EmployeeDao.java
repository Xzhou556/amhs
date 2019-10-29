package amhs.amhs.dao;

import amhs.amhs.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface EmployeeDao extends JpaRepository<Employee,Integer> {


    @Query(value ="select * from t_employee where employee_id=?",nativeQuery = true)
    Employee findId(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from t_employee where employee_id=?1",nativeQuery = true)
    Integer deleteId(Integer id);
}
