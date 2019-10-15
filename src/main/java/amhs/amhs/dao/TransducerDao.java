package amhs.amhs.dao;

import amhs.amhs.entity.Transducer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TransducerDao extends JpaRepository<Transducer,Integer> {






    //4、查询传感器在线设备
    @Query(value = " SELECT COUNT(*) FROM t_transducer WHERE device_status = 0",nativeQuery = true)
    int findInLine();

    //5、查询传感器离线设备
    @Query(value = " SELECT COUNT(*) FROM t_transducer WHERE device_status = 1",nativeQuery = true)
    int findOffLine();

    //6、模糊查询并分页
    @Query(value = "select  * from t_transducer where factory_name like concat('%',?,'%') ",nativeQuery = true)
    Page<Transducer> findLikeNameByPage(String account , Pageable pageable);


    @Query(value = "select * from t_transducer where device_id=?",nativeQuery = true)
    Transducer findId(Integer id);
}
