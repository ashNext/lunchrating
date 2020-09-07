package lunchrating.service;

import lunchrating.model.Menu;
import lunchrating.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService {

    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public List<Menu> getAll(Integer restId) {
        return repository.getAll(restId);
    }

    public Menu get(Integer id, Integer restId) {
        return repository.get(id, restId);
    }

    public boolean delete(Integer id, Integer restId) {
        return repository.delete(id, restId);
    }

    public Menu create(Menu restaurant, Integer restId) {
        return repository.save(restaurant, restId);
    }

    public void update(Menu restaurant, Integer restId) {
        repository.save(restaurant, restId);
    }

    public Menu getWithDishesOnDate(Integer restId, LocalDate date) {
        return repository.getWithDishesOnDate(restId, date);
    }
}
