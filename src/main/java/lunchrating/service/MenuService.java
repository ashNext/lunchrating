package lunchrating.service;

import lunchrating.model.Menu;
import lunchrating.repository.CrudMenuRepository;
import lunchrating.repository.CrudRestaurantRepository;
import lunchrating.util.ValidationUtil;
import lunchrating.util.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService {

    private final CrudMenuRepository repository;
    private final CrudRestaurantRepository restaurantRepository;

    public MenuService(CrudMenuRepository repository, CrudRestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Menu> getAll(int restId) {
        return repository.getAll(restId);
    }

    public Menu get(int id, int restId) {
        return repository.findById(id)
                .filter(menu -> menu.getRestaurant().getId().equals(restId))
                .orElseThrow(() -> new NotFoundException(String.format("id=%s, restId=%s", id, restId)));
    }

    public void delete(int id, int restId) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, restId) != 0, id);
    }

    @Transactional
    public Menu create(Menu menu, int restId) {
        menu.setRestaurant(restaurantRepository.getOne(restId));
        return repository.save(menu);
    }

    @Transactional
    public void update(Menu menu, int restId) {
        get(menu.getId(), restId);

        menu.setRestaurant(restaurantRepository.getOne(restId));
        repository.save(menu);
    }

    public Menu getWithDishesOnDate(int restId, LocalDate date) {
        return repository.getWithDishesOnDate(restId, date)
                .orElseThrow(() -> new NotFoundException(String.format("restId=%s, date=%s", restId, date)));
    }
}
