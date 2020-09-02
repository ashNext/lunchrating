package lunchrating.controller;

import lunchrating.model.Restaurant;
import lunchrating.service.RestaurantService;
import lunchrating.to.RestaurantTo;
import lunchrating.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    static final String REST_URL = "/rest/admin/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public List<RestaurantTo> getAllWithRateOnDate(@RequestParam @Nullable LocalDate date) {
        if (date == null) {
//            date = LocalDate.now();
            date = LocalDate.of(2020, Month.AUGUST, 27);
        }
        log.info("getAll on date={}", date);
        return service.getAllWithRateOnDate(date);
    }

    @GetMapping("all-time")
    public List<RestaurantTo> getAllWithRate() {
        log.info("getAll for all time");
        return service.getAllWithRate();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return ValidationUtil.checkNotFoundWithId(service.get(id), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        ValidationUtil.checkNotFoundWithId(service.delete(id), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        Restaurant created = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    @GetMapping("/{id}/with-menus")
    public Restaurant getWithMenusOnDate(@PathVariable int id, @RequestParam @Nullable LocalDate date) {
        if (date == null) {
//            date = LocalDate.now();
            date = LocalDate.of(2020, Month.AUGUST, 27);
        }
        log.info("get {} with menus on date={}", id, date);
        return ValidationUtil.checkNotFoundWithId(service.getWithMenusOnDate(id, date), id);
    }
}
