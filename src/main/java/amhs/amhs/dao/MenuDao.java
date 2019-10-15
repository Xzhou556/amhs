package amhs.amhs.dao;

import amhs.amhs.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuDao extends JpaRepository<Menu,Integer>, JpaSpecificationExecutor<Menu> {
    @Query(value = "select * from t_menu where menu_id = ?1",nativeQuery = true)
    Menu findId(Integer menuId);

    @Query(value = "select t.icon,t.name from t_menu t where menu_id = ?1 ",nativeQuery = true)
    Menu findNameAndIcon(Integer menuId);

    //Menu findName(Integer menuId);

    List<Menu> findByPId(Integer pId);

    @Query(value = "select * from t_menu ",nativeQuery = true)
    List<Menu> findAll();

    @Query(value = "SELECT * FROM t_menu WHERE p_id != -1",nativeQuery = true)
    List<Menu> ziCaiDan();
}
