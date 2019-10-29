package amhs.amhs.dao;

import amhs.amhs.entity.Employee;
import amhs.amhs.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface NoticeDao extends JpaRepository<Notice,Integer> {
    @Query(value ="select * from t_notice where notice_id=?",nativeQuery = true)
    Notice findId(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from t_notice where notice_id=?1",nativeQuery = true)
    Integer deleteId(Integer id);
}
