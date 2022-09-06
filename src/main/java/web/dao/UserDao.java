package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserDao {

    List<User> allUsers();

    void add(User user);

    void delete(int id);

    void edit(User user);

    User getById(int id);

}
