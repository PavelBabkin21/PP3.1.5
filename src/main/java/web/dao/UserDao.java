package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {

    List<User> allUsers();


    void save(User user);

    void delete(Long id);

    void edit(User user);

    User getById(Long id);

    User findByUsername(String username);

}