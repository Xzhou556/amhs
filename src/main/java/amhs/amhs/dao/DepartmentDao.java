package amhs.amhs.dao;

import amhs.amhs.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface DepartmentDao extends JpaRepository<Department,Integer> {

    @Query(value ="select * from t_department where department_id=?",nativeQuery = true)
    Department findId(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from t_department where department_id=?1",nativeQuery = true)
    Integer deleteId(Integer id);
}
