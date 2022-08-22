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
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.impl.NewsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoEditNews implements Command {
	
	private final NewsServiceImpl newsService = new NewsServiceImpl();
	private static final Logger log = LogManager.getRootLogger();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter(NewsParameterName.JSP_TITLE_NEWS);
		String brief = request.getParameter(NewsParameterName.JSP_BRIEF_NEWS);
		String content = request.getParameter(NewsParameterName.JSP_CONTENT_NEWS);
		Integer idNews = Integer.parseInt((String)request.getSession().getAttribute(AttributsName.NEWS_ID));
		
		HttpSession getSession = request.getSession(true);
		
		try {	
			var news = new News.Builder()
					.withId(idNews)
					.withTitle(title)
					.withBrief(brief)
					.withContent(content)
					.withDate(LocalDate.now())
					.withAuthor((User)getSession.getAttribute(AttributsName.USER))
					.build();
			if (newsService.update(news)) {				
				getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
				getSession.setAttribute(AttributsName.EDIT_NEWS, AttributsName.COMMAND_EXECUTED);
				response.sendRedirect("controller?command=go_to_news_list");					
			} else {
				response.sendRedirect(JspPageName.ERROR_PAGE);
			}
		} catch (ServiceException e) {
			log.error(e);
			response.sendRedirect(JspPageName.INDEX_PAGE);
		}		
	}
}