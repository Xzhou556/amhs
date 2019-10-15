package amhs.amhs.dao;

import amhs.amhs.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleDao extends JpaRepository<Role,Integer>, JpaSpecificationExecutor<Role> {
        @Query(value = "select * from t_role where role_id = ?1",nativeQuery = true)
        Role findId(Integer id);

}
