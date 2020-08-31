package lunchrating.repository;

import lunchrating.model.Vote;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private final CrudVoteRepository crudRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository) {
        this.crudRepository = crudVoteRepository;
    }

    @Override
    public List<Vote> getAll() {
        return crudRepository.findAll();
    }

    @Override
    public Vote get(Integer id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Integer id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Vote save(Vote vote) {
        return crudRepository.save(vote);
    }
}
