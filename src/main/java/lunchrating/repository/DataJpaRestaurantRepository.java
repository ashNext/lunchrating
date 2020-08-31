package lunchrating.repository;

import lunchrating.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {

    private final CrudRestaurantRepository crudRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRepository = crudRestaurantRepository;
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRepository.findAll();
    }

    @Override
    public Restaurant get(Integer id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Integer id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return crudRepository.save(restaurant);
    }

    @Override
    public Restaurant getWithMenus(Integer id) {
        return crudRepository.getWithMenus(id);
    }
}
