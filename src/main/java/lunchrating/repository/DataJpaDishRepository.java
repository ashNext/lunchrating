package lunchrating.repository;

import lunchrating.model.Dish;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {

    private final CrudDishRepository crudRepository;

    private final CrudMenuRepository crudMenuRepository;

    public DataJpaDishRepository(CrudDishRepository crudRepository, CrudMenuRepository crudMenuRepository) {
        this.crudRepository = crudRepository;
        this.crudMenuRepository = crudMenuRepository;
    }

    @Override
    public List<Dish> getAll(Integer menuId) {
        return crudRepository.getAll(menuId);
    }

    @Override
    public Dish get(Integer id, Integer menuId) {
        return crudRepository.findById(id)
                .filter(dish -> dish.getMenu().getId().equals(menuId))
                .orElse(null);
    }

    @Override
    public boolean delete(Integer id, Integer menuId) {
        return crudRepository.delete(id, menuId) != 0;
    }

    @Override
    public Dish save(Dish dish, Integer menuId) {
        if (dish.getId() != null && get(dish.getId(), menuId) == null) {
            return null;
        }

        dish.setMenu(crudMenuRepository.getOne(menuId));
        return crudRepository.save(dish);
    }
}
