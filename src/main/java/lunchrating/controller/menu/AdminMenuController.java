package lunchrating.controller.menu;

import lunchrating.controller.restaurant.AdminRestaurantController;
import lunchrating.model.Menu;
import lunchrating.util.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController extends AbstractMenuController {

    public static final String REST_URL = AdminRestaurantController.REST_URL + "/{restId}" + REST_URL_MENU;

    @Override
    @GetMapping("/all")
    public List<Menu> getAll(@PathVariable int restId) {
        return super.getAll(restId);
    }

    @Override
    @GetMapping("/{id}")
    public Menu get(@PathVariable int restId, @PathVariable int id) {
        return super.get(id, restId);
    }

    @Override
    @GetMapping
    public Menu getOnDate(@PathVariable int restId, @RequestParam(required = false, defaultValue = "today") LocalDate date) {
        return super.getOnDate(restId, date);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restId, @PathVariable int id) {
        super.delete(id, restId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@RequestBody Menu menu, @PathVariable int restId) {
        Menu created = super.create(menu, restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(Map.of("restId", restId, "id", created.getId()))
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Menu menu, @PathVariable int restId, @PathVariable int id) {
        super.update(menu, id, restId);
    }
}
