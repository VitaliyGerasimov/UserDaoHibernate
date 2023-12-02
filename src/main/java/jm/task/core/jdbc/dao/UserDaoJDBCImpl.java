package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS man(id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(45)," +
                " lastName VARCHAR(45)," +
                " age SMALLINT)";
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS man";
        try(Connection connection = Util.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into man(name,lastname,age) VALUES (?,?,?)";
        try(Connection connection = Util.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from man where id = ?";
        try(Connection connection = Util.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> man = new ArrayList<>();
        String sql = "select * from man";
        try(Connection connection = Util.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                User users = new User(result.getString("name"),
                        result.getString("lastName"),
                        result.getByte("age"));
                users.setId(result.getLong("id"));
                man.add(users);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return man;
    }

    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
