package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.ConnectorStatus;
import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.ServiceProvider;
import by.itacademy.matveenko.jd2.controller.AttributsName;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.controller.JspPageName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToBasePage implements Command{
	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getRootLogger();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<News> latestNews;
		int countNews = 5;
		try {
			latestNews = newsService.latestList(countNews);			
			request.setAttribute(AttributsName.NEWS, latestNews);
			request.getSession(true).setAttribute(AttributsName.PAGE_URL, "controller?command=go_to_base_page");
		} catch (ServiceException e) {			
			log.error(e);        	
		} finally {
			request.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.NOT_ACTIVE);
			request.getSession(true).setAttribute(AttributsName.PAGE_URL, "controller?command=go_to_base_page");
			request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);
		}
	}
}