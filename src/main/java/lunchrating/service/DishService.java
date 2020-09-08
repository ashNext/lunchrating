package lunchrating.service;

import lunchrating.model.Dish;
import lunchrating.repository.CrudDishRepository;
import lunchrating.repository.CrudMenuRepository;
import lunchrating.util.ValidationUtil;
import lunchrating.util.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishService {

    private final CrudDishRepository repository;
    private final CrudMenuRepository menuRepository;

    public DishService(CrudDishRepository repository, CrudMenuRepository menuRepository) {
        this.repository = repository;
        this.menuRepository = menuRepository;
    }

    public List<Dish> getAll(int menuId) {
        return repository.getAll(menuId);
    }

    public Dish get(int id, int menuId) {
        return repository.findById(id)
                .filter(dish -> dish.getMenu().getId().equals(menuId))
                .orElseThrow(() -> new NotFoundException(String.format("id=%s, menuId=%s", id, menuId)));
    }

    public void delete(int id, int menuId) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, menuId) != 0, id);
    }

    @Transactional
    public Dish create(Dish dish, int menuId) {
        dish.setMenu(menuRepository.getOne(menuId));
        return repository.save(dish);
    }

    @Transactional
    public void update(Dish dish, int menuId) {
        get(dish.getId(), menuId);

        dish.setMenu(menuRepository.getOne(menuId));
        repository.save(dish);
    }
}
