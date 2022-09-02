package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.ConnectorStatus;
import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.controller.AttributsName;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.controller.JspPageName;
import by.itacademy.matveenko.jd2.controller.NewsParameterName;
import by.itacademy.matveenko.jd2.controller.PageUrl;
import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToEditNewsPage implements Command {
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getRootLogger();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession getSession = request.getSession(true);
		News news = null;
		
		try {			
			String id = request.getParameter(NewsParameterName.JSP_ID_NEWS);
			news = newsService.findById(Integer.parseInt(id));
			if (news == null) {
				response.sendRedirect(JspPageName.ERROR_PAGE);
			} else {				
				request.setAttribute(AttributsName.NEWS, news);
				getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
				getSession.setAttribute(AttributsName.NEWS_COMMANDS_NAME, AttributsName.EDIT_NEWS);
				getSession.setAttribute(AttributsName.NEWS_ID, request.getParameter(NewsParameterName.JSP_ID_NEWS));
				StringBuilder urlForRedirect = new StringBuilder(PageUrl.EDIT_NEWS_PAGE);
				urlForRedirect.append(id);	
				getSession.setAttribute(AttributsName.PAGE_URL, urlForRedirect.toString());
				request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);
				getSession.removeAttribute(AttributsName.NEWS_COMMANDS_NAME);
			}		
		} catch (ServiceException e) {
			log.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
			}
	}
}