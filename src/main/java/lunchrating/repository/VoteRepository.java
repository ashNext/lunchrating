package lunchrating.repository;

import lunchrating.model.Vote;

import java.util.List;

public interface VoteRepository {

    List<Vote> getAll();

    Vote get(Integer id);

    boolean delete(Integer id);

    Vote save(Vote vote);
}
