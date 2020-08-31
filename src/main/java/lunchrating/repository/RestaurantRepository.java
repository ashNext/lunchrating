package lunchrating.repository;

import lunchrating.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    List<Restaurant> getAll();

    Restaurant get(Integer id);

    boolean delete(Integer id);

    Restaurant save(Restaurant restaurant);

    Restaurant getWithMenus(Integer id);
}
