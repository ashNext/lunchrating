package lunchrating.controller.menu;

import lunchrating.controller.restaurant.UserRestaurantController;
import lunchrating.model.Menu;
import lunchrating.util.ValidationUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuController extends AbstractMenuController {

    public static final String REST_URL = UserRestaurantController.REST_URL + "/{restId}" + REST_URL_MENU;

    @Override
    @GetMapping("/{id}")
    public Menu get(@PathVariable int restId, @PathVariable int id) {
        return super.get(id, restId);
    }

    @Override
    @GetMapping
    public Menu getOnDate(@PathVariable int restId, @RequestParam(required = false, defaultValue = "today") LocalDate date) {
        return ValidationUtil.checkNotFound(super.getOnDate(restId, date), "date=" + date);
    }
}
