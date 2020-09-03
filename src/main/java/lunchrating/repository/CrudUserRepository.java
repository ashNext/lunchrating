package lunchrating.repository;

import lunchrating.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudUserRepository extends JpaRepository<User, Integer> {
    User findUserByName(String userName);
}
