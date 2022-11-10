package web.service;

import org.springframework.security.core.GrantedAuthority;
import web.model.Role;
import web.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    User getUser(Long id);

    void addUser(User user);

    List<User> listUsers();

    void edit(User user, Long id);

    void delete(Long id);

    User findByUsername(String username);


    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles);

}
