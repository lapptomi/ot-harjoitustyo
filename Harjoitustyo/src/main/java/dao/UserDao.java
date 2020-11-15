package dao;

import domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void create(User user);
    User getUser(String username) throws SQLException, ClassNotFoundException;
    User update(int id);

    static List<User> getAll() {
        return null;
    }
}
