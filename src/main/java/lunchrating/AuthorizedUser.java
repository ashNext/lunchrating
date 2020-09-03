package lunchrating;

import lunchrating.model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    public AuthorizedUser(User user) {
        super(
                user.getName(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                user.getRoles());
    }
}
