package by.itacademy.matveenko.jd2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.itacademy.matveenko.jd2.bean.User;
import by.itacademy.matveenko.jd2.bean.UserRole;
import by.itacademy.matveenko.jd2.dao.DaoException;
import by.itacademy.matveenko.jd2.dao.IUserDao;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPool;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPoolException;

public class UserDao implements IUserDao {
	
	String selectUserData = "SELECT users.id as id, login, password, name, surname, email, roles.role as role FROM users JOIN roles on roles.id = users.role WHERE login=? and password=?";
    @Override
    @Deprecated
    public User findUserByLoginAndPassword(String login, String hashPassword) throws DaoException {
    	System.out.println(hashPassword);
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement ps = connection.prepareStatement(selectUserData)) {
            ps.setString(1, login);
            ps.setString(2, hashPassword);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	return new User.Builder()
                			.withId(rs.getInt("id"))
                			.withLogin(rs.getString("login"))
                            .withPassword(rs.getString("password"))
                            .withUserName(rs.getString("name"))
                            .withUserSurname(rs.getString("surname"))
                            .withEmail(rs.getString("email"))
                            .withRole(UserRole.valueOf(rs.getString("role").toUpperCase()))
                            .build();                	
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return null;
    }
    
    String selectUserLogin = "SELECT users.id as id, login, password, name, surname, email, roles.role as role FROM users JOIN roles on roles.id = users.role WHERE login=?";
    @Override
    public User findUserByLogin(String login) throws DaoException {    	
    	try (Connection connection = ConnectionPool.getInstance().takeConnection();
                PreparedStatement ps = connection.prepareStatement(selectUserLogin)) {
                ps.setString(1, login);                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                    	return new User.Builder()
                    			.withId(rs.getInt("id"))
                    			.withLogin(rs.getString("login"))
                                .withPassword(rs.getString("password"))
                                .withUserName(rs.getString("name"))
                                .withUserSurname(rs.getString("surname"))
                                .withEmail(rs.getString("email"))
                                .withRole(UserRole.valueOf(rs.getString("role").toUpperCase()))
                                .build();                	
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            } catch (ConnectionPoolException e) {
                throw new DaoException(e);
            }
            return null;
        }

    String insertRegistrationData = "INSERT INTO users(login, password, name, surname, email, role) VALUES (?,?,?,?,?,?)";
    @Override
    public boolean saveUser(User user) throws DaoException {        
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement ps = connection.prepareStatement(insertRegistrationData)) {
        	ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getUserSurname());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getRole().getRole());
            ps.executeUpdate();
        } catch (SQLException e) {            
        	throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return true;
    }
    
    String selectDataFindById = "SELECT users.id as id, login, password, name, surname, email, roles.role as role FROM users JOIN roles on roles.id = users.role WHERE users.id=?";
    @Override
    public User findById(Integer id) throws SQLException, DaoException {    	
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(selectDataFindById)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User.Builder()
                            .withId(rs.getInt("id"))
                            .withLogin(rs.getString("login"))
                            .withPassword(rs.getString("password"))
                            .withUserName(rs.getString("name"))
                            .withUserSurname(rs.getString("surname"))
                            .withEmail(rs.getString("email"))
                            .withRole(UserRole.valueOf(rs.getString("role").toUpperCase()))
                            .build();
                }
            } catch (SQLException e) {
            	throw new DaoException(e);
            }
        } catch (ConnectionPoolException e) {
            throw new DaoException();
        }
        return null;
    }    
}