package lunchrating.service;

import lunchrating.model.Vote;
import lunchrating.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public List<Vote> getAll() {
        return repository.getAll();
    }

    public Vote get(Integer id) {
        return repository.get(id);
    }

    public boolean delete(Integer id) {
        return repository.delete(id);
    }

    public Vote create(Vote vote) {
        return repository.save(vote);
    }

    public void update(Vote vote) {
        repository.save(vote);
    }
}
