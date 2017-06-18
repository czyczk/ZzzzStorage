package dao;

import module.User;

import java.util.List;

/**
 * Created by czyczk on 2017-6-18.
 */
public interface IUserDao {
    void add(User user);
    void delete(int id);
    void update(User user);
    User load(int id);
    User load(String email);
    List<User> list();
    User login(String email, String password);
}
