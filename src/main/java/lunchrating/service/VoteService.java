package lunchrating.service;

import lunchrating.model.User;
import lunchrating.model.Vote;
import lunchrating.repository.CrudRestaurantRepository;
import lunchrating.repository.CrudVoteRepository;
import lunchrating.util.DateTimeUtil;
import lunchrating.util.exception.DeniedToVoteException;
import lunchrating.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteService {

    private final CrudVoteRepository repository;
    private final CrudRestaurantRepository restaurantRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DateTimeUtil.SystemClock clock;

    public VoteService(CrudVoteRepository repository, CrudRestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    public Vote get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Transactional
    public void voting(int restId, User user, LocalDate date) {
        Vote vote = getByUserAndDate(user, date);

        LocalTime nowTime = LocalTime.now(clock);
        log.info("current time = {}", nowTime);

        if (vote.getId() != null && !nowTime.isBefore(LocalTime.of(11, 0, 0))) {
            throw new DeniedToVoteException(String.format("restId=%s, userId=%s, date=%s", restId, user.getId(), date));
        }

        vote.setRestaurant(restaurantRepository.getOne(restId));
        repository.save(vote);
    }

    public Vote getByUserAndDate(User user, LocalDate date) {
        return repository.getByUserAndDate(user, date)
                .orElse(new Vote(null, user, date));
    }
}
