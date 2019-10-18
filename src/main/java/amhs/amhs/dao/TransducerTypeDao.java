package amhs.amhs.dao;

import amhs.amhs.entity.TransducerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TransducerTypeDao extends JpaRepository<TransducerType,Integer> {


    @Query(value = "select * from t_transducertype where tt_id = ?",nativeQuery = true)
    TransducerType findId(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from t_transducertype where tt_id=?1",nativeQuery = true)
    Integer deleteId(Integer id);
}
