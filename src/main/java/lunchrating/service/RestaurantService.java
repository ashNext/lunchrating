package lunchrating.service;

import lunchrating.model.Restaurant;
import lunchrating.repository.RestaurantRepository;
import lunchrating.to.RestaurantTo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public Restaurant get(Integer id) {
        return repository.get(id);
    }

    public boolean delete(Integer id) {
        return repository.delete(id);
    }

    public Restaurant create(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        repository.save(restaurant);
    }

    public Restaurant getWithMenus(Integer id) {
        return repository.getWithMenus(id);
    }

    public Restaurant getWithMenusOnDate(int id, LocalDate date) {
        return repository.getWithMenusOnDate(id, date);
    }

    public List<RestaurantTo> getAllWithRate() {
        return repository.getAllWithRate();
    }

    public List<RestaurantTo> getAllWithRateOnDate(LocalDate date) {
        return repository.getAllWithRateOnDate(date);
    }
}
