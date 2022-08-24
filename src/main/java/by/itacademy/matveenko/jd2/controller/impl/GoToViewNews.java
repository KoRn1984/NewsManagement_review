package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.controller.AttributsName;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.controller.JspPageName;
import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToViewNews implements Command {
	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getRootLogger();	
	private static final String NEWS_ID = "id";
		
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		News news;		
		String id = request.getParameter(NEWS_ID);
				
		try {
			news = newsService.findById(Integer.parseInt(id));
			request.setAttribute(AttributsName.NEWS, news);		
			request.setAttribute(AttributsName.PRESENTATION, AttributsName.VIEW_NEWS);
			request.getSession(true).setAttribute(AttributsName.PAGE_URL, "controller?command=go_to_view_news&id=" + id);
			//System.out.println(request.getHeader("referer"));
			request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);		
		} catch (ServiceException e) {		
			log.error(e);
        	response.sendRedirect(JspPageName.ERROR_PAGE);
		}		
	}
}