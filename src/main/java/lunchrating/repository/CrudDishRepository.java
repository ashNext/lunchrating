package lunchrating.repository;

import lunchrating.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id and d.menu.id=:menuId")
    int delete(@Param("id") int id, @Param("menuId") int menuId);

    @Query("SELECT d FROM Dish d WHERE d.menu.id=:menuId ORDER BY d.name")
    List<Dish> getAll(@Param("menuId") int menuId);
}
