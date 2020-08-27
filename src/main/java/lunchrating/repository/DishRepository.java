package lunchrating.repository;

import lunchrating.model.Dish;

import java.util.List;

public interface DishRepository {

    List<Dish> getAll(Integer menuId);

    Dish get(Integer id, Integer menuId);

    boolean delete(Integer id, Integer menuId);

    Dish save(Dish dish, Integer menuId);
}
