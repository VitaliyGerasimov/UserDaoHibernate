package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();

        User user1 = new User("Anton", "Echmaev", (byte) 25);
        User user2 = new User("Alexander", "Anikeev", (byte) 28);
        User user3 = new User("Marat", "Baygazin", (byte) 27);
        userDao.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userDao.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userDao.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        List<User> users = userDao.getAllUsers();
        users.forEach(u -> System.out.println(u.getName()));
        userDao.removeUserById(1);
        userDao.getAllUsers();
//        userDao.cleanUsersTable();
//        userDao.dropUsersTable();
    }
}
