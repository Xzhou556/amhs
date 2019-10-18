package amhs.amhs.dao;

import amhs.amhs.entity.Factory;
import amhs.amhs.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface FactoryDao extends JpaRepository<Factory,Integer>, JpaSpecificationExecutor<Factory> {
    //
    @Query(value="select * from t_factory where factory_id = ?1",nativeQuery = true)
    public Factory findId(Integer factoryId);


    //4、计算出工厂得总数量
    @Query(value = "select count(*) from t_factory",nativeQuery = true)
    int totalFactory();


    //6根据工厂名称模糊查询并遍历
    Page<Factory> findByFactoryNameContaining(String factoryName, Pageable pageable);

    List<Factory> findByFactoryNameContaining(String factoryName);

    //7、模糊查询并分页
    @Query(value = "select  * from t_factory where factory_name like concat('%',?,'%') ",nativeQuery = true)
    Page<Factory> findLikeNameByPage(String account , Pageable pageable);

    //8、查询所有工厂数据并分页
    Page<Factory> findAll(Pageable pageable);
    @Query(value = "select * from t_factory where factory_id = ?",nativeQuery = true)
    Factory findFactoryId(Integer factoryId);


}
