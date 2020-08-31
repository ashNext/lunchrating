package lunchrating.repository;

import lunchrating.model.Menu;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaMenuRepository implements MenuRepository {

    private final CrudMenuRepository crudRepository;

    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaMenuRepository(CrudMenuRepository crudRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRepository = crudRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public List<Menu> getAll(Integer restId) {
        return crudRepository.getAll(restId);
    }

    @Override
    public Menu get(Integer id, Integer restId) {
        return crudRepository.findById(id)
                .filter(menu -> menu.getRestaurant().getId().equals(restId))
                .orElse(null);
    }

    @Override
    public boolean delete(Integer id, Integer restId) {
        return crudRepository.delete(id, restId) != 0;
    }

    @Override
    public Menu save(Menu menu, Integer restId) {
        if (menu.getId() != null && get(menu.getId(), restId) == null) {
            return null;
        }

        menu.setRestaurant(crudRestaurantRepository.getOne(restId));
        return crudRepository.save(menu);
    }

    @Override
    public Menu getWithDishes(Integer id, Integer restId) {
        return crudRepository.getWithDishes(id, restId);
    }

    @Override
    public Menu getWithDishesOnDate(int restId, LocalDate date) {
        return crudRepository.getWithDishesOnDate(restId, date);
    }
}
