package lunchrating.repository;

import lunchrating.model.Restaurant;
import lunchrating.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {

    List<Restaurant> getAll();

    Restaurant get(Integer id);

    boolean delete(Integer id);

    Restaurant save(Restaurant restaurant);

    Restaurant getWithMenusOnDate(int id, LocalDate date);

    List<RestaurantTo> getAllWithRate();

    List<RestaurantTo> getAllWithRateOnDate(LocalDate date);
}
