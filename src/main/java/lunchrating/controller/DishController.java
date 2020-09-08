package lunchrating.controller;

import lunchrating.controller.menu.AdminMenuController;
import lunchrating.model.Dish;
import lunchrating.service.DishService;
import lunchrating.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    static final String REST_URL_DISH = "/dish";
    static final String REST_URL = AdminMenuController.REST_URL + "/{menuId}" + REST_URL_DISH;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dish> getAll(@PathVariable int menuId) {
        log.info("getAll");
        return service.getAll(menuId);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int menuId, @PathVariable int id) {
        log.info("get {}", id);
        return service.get(id, menuId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int menuId, @PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id, menuId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@PathVariable int restId, @PathVariable int menuId, @RequestBody Dish dish) {
        log.info("create {} for menu {}", dish, menuId);
        ValidationUtil.checkNew(dish);
        Dish created = service.create(dish, menuId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(Map.of("restId", restId, "menuId", menuId, "id", created.getId()))
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable int menuId, @RequestBody Dish dish, @PathVariable int id) {
        log.info("update {} with id={}", dish, id);
        ValidationUtil.assureIdConsistent(dish, id);
        service.update(dish, menuId);
    }
}
