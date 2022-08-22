package by.itacademy.matveenko.jd2.service.impl;

import by.itacademy.matveenko.jd2.dao.IUserDao;
import by.itacademy.matveenko.jd2.service.IUserService;
import by.itacademy.matveenko.jd2.util.validation.UserDataValidation;
import by.itacademy.matveenko.jd2.util.validation.ValidationProvider;
import by.itacademy.matveenko.jd2.bean.User;
import by.itacademy.matveenko.jd2.dao.DaoException;
import by.itacademy.matveenko.jd2.dao.DaoProvider;
import by.itacademy.matveenko.jd2.service.ServiceException;

public class UserServiceImpl implements IUserService{
	
	private final IUserDao userDao = DaoProvider.getInstance().getUserDao();
	private final UserDataValidation userDataValidation = ValidationProvider. getInstance().getUserDataValidation();
	
	@Override
	public User signIn(String login, String password) throws ServiceException {
		if (!userDataValidation.checkAuthDataLogination(login,password)) {
			throw new ServiceException("Invalid authorization data!");
   	 }
		try {
			User user = userDao.findUserByLoginAndPassword(login, password);			
			if(user != null) {
				return user;				
				} else {
					return null;
				}
			}catch(DaoException e) {
				throw new ServiceException(e);
			}
		}
		
	@Override
	public boolean registration(User user) throws ServiceException  {
		  if (!userDataValidation.checkAuthDataRegistration(user)) {
			  throw new ServiceException("Invalid registration data!");
	  }
		  try {
			   return userDao.saveUser(user);			  
		   }catch(DaoException e) {
				throw new ServiceException(e);
				}		  
		  }
	}