package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.service.IUserService;
import by.itacademy.matveenko.jd2.bean.ConnectorStatus;
import by.itacademy.matveenko.jd2.bean.User;
import by.itacademy.matveenko.jd2.bean.UserRole;
import by.itacademy.matveenko.jd2.controller.AttributsName;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.controller.JspPageName;
import by.itacademy.matveenko.jd2.controller.UserParameterName;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DoSignIn implements Command {

	private final IUserService service = ServiceProvider.getInstance().getUserService();
	private static final Logger log = LogManager.getRootLogger();


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(UserParameterName.JSP_LOGIN_PARAM);
		String password = request.getParameter(UserParameterName.JSP_PASSWORD_PARAM);	

		if (!dataValidation(login, password)) {
            response.sendRedirect(JspPageName.INDEX_PAGE);
            return;
        }
		try {
			User user = service.signIn(login, password);
			if (user == null) {				
				request.getSession(true).setAttribute(AttributsName.USER_STATUS, ConnectorStatus.NOT_ACTIVE);
				request.getSession(true).setAttribute(AttributsName.ROLE, UserRole.GUEST);
				response.sendRedirect("controller?command=go_to_base_page&AuthenticationError=Wrong login or password!");
			} else if (!user.getRole().equals(UserRole.GUEST)) {
				request.getSession(true).setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
				request.getSession(true).setAttribute(AttributsName.ROLE, user.getRole().getName());
				request.getSession(true).setAttribute(AttributsName.USER, user);
				response.sendRedirect("controller?command=go_to_news_list");
			} 
		} catch (ServiceException e) {
			log.error(e);
			response.sendRedirect(JspPageName.INDEX_PAGE);
		}		
	}
	
	private boolean dataValidation(String login, String password) {
        if (login == null || password == null) {
            return false;
        }
        return true;
    }
}