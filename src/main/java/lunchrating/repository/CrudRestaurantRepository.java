package lunchrating.repository;

import lunchrating.model.Restaurant;
import lunchrating.to.RestaurantTo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r LEFT JOIN r.menus m WHERE r.id=:id AND (m.date=:date OR m.date IS NULL)")
    Optional<Restaurant> getWithMenusOnDate(@Param("id") int id, @Param("date") LocalDate date);

    @Query("SELECT new lunchrating.to.RestaurantTo(r, count(v)) " +
            "FROM Restaurant r LEFT JOIN r.votes v GROUP BY r")
    List<RestaurantTo> getAllWithRate();

    @Query("SELECT new lunchrating.to.RestaurantTo(r, count(v)) " +
            "FROM Restaurant r LEFT JOIN r.votes v WHERE v.date=:date OR v.date IS NULL GROUP BY r")
    List<RestaurantTo> getAllWithRateOnDate(@Param("date") LocalDate date);
}
