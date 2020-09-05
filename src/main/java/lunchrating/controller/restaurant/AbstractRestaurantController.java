package lunchrating.controller.restaurant;

import lunchrating.model.Restaurant;
import lunchrating.service.RestaurantService;
import lunchrating.to.RestaurantTo;
import lunchrating.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<RestaurantTo> getAllWithRateOnDate(LocalDate date) {
        log.info("getAll on date {}", date);
        return service.getAllWithRateOnDate(date);
    }

    public List<RestaurantTo> getAllWithRate() {
        log.info("getAll for all time");
        return service.getAllWithRate();
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return ValidationUtil.checkNotFoundWithId(service.get(id), id);
    }

    public Restaurant getWithMenusOnDate(int id, LocalDate date) {
        log.info("get {} with menus on date {}", id, date);
        return ValidationUtil.checkNotFoundWithId(service.getWithMenusOnDate(id, date), id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        ValidationUtil.checkNotFoundWithId(service.delete(id), id);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        return service.create(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id {}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }
}
