package amhs.amhs.dao;

import amhs.amhs.entity.UserFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UserFactoryDao extends JpaRepository<UserFactory,Integer> {
    @Query(value = "select * from  user_factory where factory_id =? ",nativeQuery = true)
    UserFactory findFactoryId(Integer factoryId);

    @Query(value = "select * from  user_factory where user_id =? ",nativeQuery = true)
    UserFactory findUserId(Integer userId);

    @Transactional
    @Modifying
    @Query(value = "delete from user_factory where user_id = ?",nativeQuery = true)
    Integer deleteUserId(Integer userId);
}
