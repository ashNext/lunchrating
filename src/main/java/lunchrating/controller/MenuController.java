package lunchrating.controller;

import lunchrating.model.Menu;
import lunchrating.service.MenuService;
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
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    static final String REST_URL_MENU = "/menu";
    static final String REST_URL = RestaurantController.REST_URL + "/{restId}" + REST_URL_MENU;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping
    public List<Menu> getAll(@PathVariable int restId) {
        log.info("getAll");
        return service.getAll(restId);
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable int restId, @PathVariable int id) {
        log.info("get {}", id);
        return ValidationUtil.checkNotFoundWithId(service.get(id, restId), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restId, @PathVariable int id) {
        log.info("delete {}", id);
        ValidationUtil.checkNotFoundWithId(service.delete(id, restId), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@PathVariable int restId, @RequestBody Menu menu) {
        log.info("create {} for restaurant {}", menu, restId);
        ValidationUtil.checkNew(menu);
        Menu created = service.create(menu, restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(Map.of("restId", restId, "id", created.getId()))
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restId, @RequestBody Menu menu, @PathVariable int id) {
        log.info("update {} with id={}", menu, id);
        ValidationUtil.assureIdConsistent(menu, id);
        service.update(menu, restId);
    }
}
