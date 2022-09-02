package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.ConnectorStatus;
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

public class DoDeleteNews implements Command {
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getRootLogger();	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String[] idNewses = request.getParameterValues(NewsParameterName.JSP_ID_NEWS);		
		    HttpSession getSession = request.getSession(true);
						
			try {				
				if (newsService.deleteNewsesByIds(idNewses)) {			
					getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);					
					getSession.setAttribute(AttributsName.DELETE_NEWS, AttributsName.COMMAND_EXECUTED);
					response.sendRedirect(PageUrl.NEWS_LIST_PAGE);
				} else {
					response.sendRedirect(JspPageName.ERROR_PAGE);
				}
			} catch (ServiceException e) {
				log.error(e);
				response.sendRedirect(JspPageName.INDEX_PAGE);
			}
	}
}