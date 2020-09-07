package lunchrating.repository;

import lunchrating.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restId")
    int delete(@Param("id") int id, @Param("restId") int restId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restId")
    List<Menu> getAll(@Param("restId") int restId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restId AND m.date=:date")
    Optional<Menu> getWithDishesOnDate(@Param("restId") int restId, @Param("date") LocalDate date);
}
