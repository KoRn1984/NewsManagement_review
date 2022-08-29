package by.itacademy.matveenko.jd2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.itacademy.matveenko.jd2.bean.User;
import by.itacademy.matveenko.jd2.bean.UserRole;
import by.itacademy.matveenko.jd2.controller.UserParameterName;
import by.itacademy.matveenko.jd2.dao.DaoException;
import by.itacademy.matveenko.jd2.dao.IUserDao;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPool;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPoolException;

public class UserDao implements IUserDao {	
	
	private static final String SELECT_USER_DATA = "SELECT users.id AS id, login, password, name, surname, email, roles.role AS role FROM users JOIN roles ON roles.id = users.role WHERE login = ? AND password = ?";
    @Override
    @Deprecated
    public User findUserByLoginAndPassword(String login, String hashPassword) throws DaoException {
    	try (Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_DATA)) {
            ps.setString(1, login);
            ps.setString(2, hashPassword);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	return new User.Builder()
                			.withId(rs.getInt(UserParameterName.JSP_ID_PARAM))
                			.withLogin(rs.getString(UserParameterName.JSP_LOGIN_PARAM))
                            .withPassword(rs.getString(UserParameterName.JSP_PASSWORD_PARAM))
                            .withUserName(rs.getString(UserParameterName.JSP_NAME_PARAM))
                            .withUserSurname(rs.getString(UserParameterName.JSP_SURNAME_PARAM))
                            .withEmail(rs.getString(UserParameterName.JSP_EMAIL_PARAM))
                            .withRole(UserRole.valueOf(rs.getString(UserParameterName.JSP_ROLE_PARAM).toUpperCase()))
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
    
    private static final String SELECT_USER_LOGIN = "SELECT users.id AS id, login, password, name, surname, email, roles.role AS role FROM users JOIN roles ON roles.id = users.role WHERE login = ?";
    @Override
    public User findUserByLogin(String login) throws DaoException {    	
    	try (Connection connection = ConnectionPool.getInstance().takeConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_USER_LOGIN)) {
                ps.setString(1, login);                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                    	return new User.Builder()
                    			.withId(rs.getInt(UserParameterName.JSP_ID_PARAM))
                    			.withLogin(rs.getString(UserParameterName.JSP_LOGIN_PARAM))
                                .withPassword(rs.getString(UserParameterName.JSP_PASSWORD_PARAM))
                                .withUserName(rs.getString(UserParameterName.JSP_NAME_PARAM))
                                .withUserSurname(rs.getString(UserParameterName.JSP_SURNAME_PARAM))
                                .withEmail(rs.getString(UserParameterName.JSP_EMAIL_PARAM))
                                .withRole(UserRole.valueOf(rs.getString(UserParameterName.JSP_ROLE_PARAM).toUpperCase()))
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

    private static final String INSERT_REGISTRATION_DATA = "INSERT INTO users(login, password, name, surname, email, role) VALUES (?, ?, ?, ?, ?, ?)";
    @Override
    public boolean saveUser(User user) throws DaoException {        
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_REGISTRATION_DATA)) {
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
    
    private static final String SELECT_DATA_FIND_BY_ID = "SELECT users.id AS id, login, password, name, surname, email, roles.role AS role FROM users JOIN roles ON roles.id = users.role WHERE users.id = ?";
    @Override
    public User findById(Integer id) throws SQLException, DaoException {    	
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_DATA_FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User.Builder()
                            .withId(rs.getInt(UserParameterName.JSP_ID_PARAM))
                            .withLogin(rs.getString(UserParameterName.JSP_LOGIN_PARAM))
                            .withPassword(rs.getString(UserParameterName.JSP_PASSWORD_PARAM))
                            .withUserName(rs.getString(UserParameterName.JSP_NAME_PARAM))
                            .withUserSurname(rs.getString(UserParameterName.JSP_SURNAME_PARAM))
                            .withEmail(rs.getString(UserParameterName.JSP_EMAIL_PARAM))
                            .withRole(UserRole.valueOf(rs.getString(UserParameterName.JSP_ROLE_PARAM).toUpperCase()))
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