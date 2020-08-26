package lunchrating.repository;

import lunchrating.model.Dish;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {

    private final CrudDishRepository crudRepository;

    public DataJpaDishRepository(CrudDishRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public List<Dish> getAll(Integer menuId) {
        return crudRepository.getAll(menuId);
    }

    @Override
    public Dish get(Integer id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Integer id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Dish save(Dish dish) {
        return crudRepository.save(dish);
    }
}
