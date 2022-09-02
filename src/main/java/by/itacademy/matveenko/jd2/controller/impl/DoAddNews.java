package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;
import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.ConnectorStatus;
import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.bean.User;
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

public class DoAddNews implements Command {
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getRootLogger();
	private static final String ERROR_ADD_NEWS_MESSAGE = "&AddNewsError";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String title = request.getParameter(NewsParameterName.JSP_TITLE_NEWS);
			String brief = request.getParameter(NewsParameterName.JSP_BRIEF_NEWS);
			String content = request.getParameter(NewsParameterName.JSP_CONTENT_NEWS);
			HttpSession getSession = request.getSession(true);
						
			var user = (User) getSession.getAttribute(AttributsName.USER);
			News news = new News.Builder()
					.withTitle(title)
                    .withBrief(brief)
                    .withContent(content)
                    .withDate(LocalDate.now())
                    .withAuthor(user)
                    .build();
			try {				
				if (newsService.save(news)) {					
					getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
					getSession.setAttribute(AttributsName.ADD_NEWS, AttributsName.COMMAND_EXECUTED);					
					response.sendRedirect(PageUrl.NEWS_LIST_PAGE);
				} else {
					response.sendRedirect(JspPageName.ERROR_PAGE);
				}
			} catch (ServiceException e) {
				log.error(e);
				StringBuilder urlForRedirect = new StringBuilder(PageUrl.ADD_NEWS_PAGE);
				urlForRedirect.append(ERROR_ADD_NEWS_MESSAGE);				
				response.sendRedirect(urlForRedirect.toString());		
			}
	}
}