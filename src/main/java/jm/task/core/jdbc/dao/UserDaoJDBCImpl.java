package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS User(id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(45)," +
                " lastName VARCHAR(45)," +
                " age SMALLINT)";
        try (Connection connection = Util.getInstance().getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

        public void dropUsersTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS User";
        try(Connection connection = Util.getInstance().getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new SQLException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "insert into User(name,lastname,age) VALUES (?,?,?)";
        try(Connection connection = Util.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
    } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sql = "delete from User where id = ?";
        try(Connection connection = Util.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> User = new ArrayList<>();
        String sql = "select * from User";
        try(Connection connection = Util.getInstance().getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                User users = new User(result.getString("name"),
                        result.getString("lastName"),
                        result.getByte("age"));
                users.setId(result.getLong("id"));
                User.add(users);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
        return User;
    }

    public void cleanUsersTable() throws SQLException {
        dropUsersTable();
        createUsersTable();
    }
}
