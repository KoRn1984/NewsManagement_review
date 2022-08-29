package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.IUserService;
import by.itacademy.matveenko.jd2.bean.ConnectorStatus;
import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.bean.User;
import by.itacademy.matveenko.jd2.bean.UserRole;
import by.itacademy.matveenko.jd2.controller.AttributsName;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.controller.JspPageName;
import by.itacademy.matveenko.jd2.controller.PageUrl;
import by.itacademy.matveenko.jd2.controller.UserParameterName;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoSignIn implements Command {

	private final IUserService service = ServiceProvider.getInstance().getUserService();
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getRootLogger();
	private static final String ERROR_SIGN_IN_MESSAGE = "&AuthenticationError=Wrong login or password!";
	private static final int COUNT_NEWS = 5;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(UserParameterName.JSP_LOGIN_PARAM);
		String password = request.getParameter(UserParameterName.JSP_PASSWORD_PARAM);
		String local = request.getParameter(AttributsName.LOCAL);
		List<News> latestNews;
		
		if (!dataValidation(login, password)) {
            response.sendRedirect(JspPageName.INDEX_PAGE);
            return;
        }
		try {
			HttpSession getSession = request.getSession(true);
			User user = service.signIn(login, password);
			latestNews = newsService.latestList(COUNT_NEWS);
			if (user == null) {				
				getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.NOT_ACTIVE);
				getSession.removeAttribute(AttributsName.REGISTER_USER);
				getSession.setAttribute(AttributsName.ROLE, UserRole.GUEST);
				request.setAttribute(AttributsName.NEWS, latestNews);				
				StringBuilder urlForRedirect = new StringBuilder(PageUrl.BASE_PAGE);
				urlForRedirect.append(ERROR_SIGN_IN_MESSAGE);
				urlForRedirect.append(PageUrl.AMPERSAND_LOCAL);
				urlForRedirect.append(local);
				response.sendRedirect(urlForRedirect.toString());				
			} else if (!user.getRole().equals(UserRole.GUEST)) {
				getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
				getSession.setAttribute(AttributsName.ROLE, user.getRole().getName());
				getSession.setAttribute(AttributsName.USER, user);
				getSession.removeAttribute(AttributsName.REGISTER_USER);
				request.setAttribute(AttributsName.NEWS, latestNews);
				StringBuilder urlForRedirect = new StringBuilder(PageUrl.NEWS_LIST_PAGE);
				urlForRedirect.append(PageUrl.AMPERSAND_LOCAL);
				urlForRedirect.append(local);
				response.sendRedirect(urlForRedirect.toString());
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