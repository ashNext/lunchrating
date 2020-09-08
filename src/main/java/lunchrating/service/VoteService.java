package lunchrating.service;

import lunchrating.model.User;
import lunchrating.model.Vote;
import lunchrating.repository.CrudRestaurantRepository;
import lunchrating.repository.CrudVoteRepository;
import lunchrating.util.exception.DeniedToVoteException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteService {

    private final CrudVoteRepository repository;
    private final CrudRestaurantRepository restaurantRepository;


    public VoteService(CrudVoteRepository repository, CrudRestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public void voting(int restId, User user, LocalDate date) {
        Vote vote = getByUserAndDate(user, date);

        if (vote.getId() != null && !LocalTime.now().isBefore(LocalTime.of(23, 0, 0))) {
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
