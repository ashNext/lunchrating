package lunchrating.service;

import lunchrating.AuthorizedUser;
import lunchrating.model.User;
import lunchrating.repository.CrudUserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements UserDetailsService {
    private final CrudUserRepository repository;

    public UserService(CrudUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuthorizedUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = repository.findUserByName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User " + userName + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
