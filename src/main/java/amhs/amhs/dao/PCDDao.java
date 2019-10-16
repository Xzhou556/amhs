package amhs.amhs.dao;

import amhs.amhs.entity.PCD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PCDDao extends JpaRepository<PCD,Integer> {
    @Query(nativeQuery = true,
            value ="select * from province_city_district where pid =:pid")
    List<PCD> findPCDByPid(@Param("pid")int pid);


}
