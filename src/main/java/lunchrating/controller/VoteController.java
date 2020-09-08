package lunchrating.controller;

import lunchrating.AuthorizedUser;
import lunchrating.controller.restaurant.UserRestaurantController;
import lunchrating.model.User;
import lunchrating.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    public static final String REST_URL = UserRestaurantController.REST_URL;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService service;

    public VoteController(VoteService service) {
        this.service = service;
    }

    @PostMapping(value = "/{restId}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void voting(@PathVariable int restId, @AuthenticationPrincipal AuthorizedUser authUser) {
        LocalDate nowDate = LocalDate.now();
        User user = authUser.getUser();
        log.info("vote user {} for restaurant {} on date {}", user.getId(), restId, nowDate);
        service.voting(restId, user, nowDate);
    }
}
