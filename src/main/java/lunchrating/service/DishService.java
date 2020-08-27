package lunchrating.service;

import lunchrating.model.Dish;
import lunchrating.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public List<Dish> getAll(Integer restId) {
        return repository.getAll(restId);
    }

    public Dish get(Integer id, Integer menuId) {
        return repository.get(id, menuId);
    }

    public boolean delete(Integer id, Integer menuId) {
        return repository.delete(id, menuId);
    }

    public Dish create(Dish restaurant, Integer menuId) {
        return repository.save(restaurant, menuId);
    }

    public void update(Dish restaurant, Integer menuId) {
        repository.save(restaurant, menuId);
    }
}
