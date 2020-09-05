package lunchrating.controller.restaurant;

import lunchrating.model.Restaurant;
import lunchrating.to.RestaurantTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/rest/restaurants";

    @Override
    @GetMapping
    public List<RestaurantTo> getAllWithRateOnDate(@RequestParam(required = false, defaultValue = "today") LocalDate date) {
        return super.getAllWithRateOnDate(date);
    }

    @Override
    @GetMapping("/all-time")
    public List<RestaurantTo> getAllWithRate() {
        return super.getAllWithRate();
    }

    @Override
    @GetMapping("/{id}/with-menus")
    public Restaurant getWithMenusOnDate(@PathVariable int id,
                                         @RequestParam(required = false, defaultValue = "today") LocalDate date) {
        return super.getWithMenusOnDate(id, date);
    }
}
