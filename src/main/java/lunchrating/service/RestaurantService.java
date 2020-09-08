package lunchrating.service;

import lunchrating.model.Restaurant;
import lunchrating.repository.CrudRestaurantRepository;
import lunchrating.to.RestaurantTo;
import lunchrating.util.ValidationUtil;
import lunchrating.util.exception.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService {

    private final CrudRestaurantRepository repository;

    public RestaurantService(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Restaurant get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Transactional
    public Restaurant create(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Transactional
    public void update(Restaurant restaurant) {
        get(restaurant.getId());

        repository.save(restaurant);
    }

    public Restaurant getWithMenusOnDate(int id, LocalDate date) {
        return repository.getWithMenusOnDate(id, date)
                .orElseThrow(() -> new NotFoundException(String.format("id=%s, date=%s", id, date)));
    }

    public List<RestaurantTo> getAllWithRate() {
        return repository.getAllWithRate();
    }

    public List<RestaurantTo> getAllWithRateOnDate(LocalDate date) {
        return repository.getAllWithRateOnDate(date);
    }
}
