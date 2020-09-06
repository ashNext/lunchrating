package lunchrating.controller;

import lunchrating.AuthorizedUser;
import lunchrating.controller.restaurant.UserRestaurantController;
import lunchrating.model.Restaurant;
import lunchrating.model.User;
import lunchrating.model.Vote;
import lunchrating.service.RestaurantService;
import lunchrating.service.VoteService;
import lunchrating.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    public static final String REST_URL = UserRestaurantController.REST_URL;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService service;
    private final RestaurantService restaurantService;

    public VoteController(VoteService service, RestaurantService restaurantService) {
        this.service = service;
        this.restaurantService = restaurantService;
    }

    @PostMapping(value = "/{restId}/vote")
    public boolean voted(@PathVariable int restId, @AuthenticationPrincipal AuthorizedUser authUser) {
        LocalDate nowDate = LocalDate.now();
        User user = authUser.getUser();
        log.info("vote user {} for restaurant {} on date {}", user.getId(), restId, nowDate);
        Restaurant restaurant = restaurantService.get(restId);

        Vote vote = new Vote(restaurant, user, nowDate);
        Vote existVote = service.getByUserAndDate(user, nowDate);

        if (existVote == null) {
            log.info("new vote user {} for restaurant {} on date {}", user.getId(), restId, nowDate);
            return service.create(vote) != null;
        }

        if (existVote.getRestaurant().getId() == restId) {
            log.info("restaurant {} = {}", existVote.getRestaurant().getId(), restId);
            return false;
        }

        if (LocalTime.now().isAfter(LocalTime.of(22, 59, 59))) {
            log.info("time {} after {}", LocalTime.now(), LocalTime.of(10, 59, 59));
            return false;
        }

        log.info("update vote {} user {} for restaurant {} on date {}", vote.getId(), vote.getUser().getId(), vote.getRestaurant().getId(), vote.getDate());
        ValidationUtil.assureIdConsistent(vote, existVote.getId());
        service.update(vote);
        return true;
    }
}
