package lunchrating.repository;

import lunchrating.model.Dish;

import java.util.List;

public interface DishRepository {

    List<Dish> getAll(Integer menuId);

    Dish get(Integer id);

    boolean delete(Integer id);

    Dish save(Dish dish);
}
