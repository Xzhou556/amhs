package amhs.amhs.dao;

import amhs.amhs.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInfoDao extends JpaRepository<UserInfo,Integer> , JpaSpecificationExecutor<UserInfo> {


    UserInfo findByAccount(String account);

    @Query(value = "select * from t_userinfo where user_id=?1",nativeQuery = true)
    UserInfo findId(Integer id);

    @Query(value = "SELECT * FROM t_userinfo WHERE account =? ",nativeQuery = true)
    UserInfo findAccount(String account);

}
