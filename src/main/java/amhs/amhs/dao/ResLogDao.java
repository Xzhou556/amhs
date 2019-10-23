package amhs.amhs.dao;

import amhs.amhs.entity.ResLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ResLogDao extends JpaRepository<ResLog,Integer> {
    @Modifying
    @Transactional
    @Query(value = "delete from res_log where log_id=?",nativeQuery = true)
    Integer deleteLogID(Integer id);
}
