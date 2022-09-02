package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.controller.AttributsName;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.controller.JspPageName;
import by.itacademy.matveenko.jd2.controller.PageUrl;
import by.itacademy.matveenko.jd2.service.INewsService;
import by.itacademy.matveenko.jd2.service.ServiceException;
import by.itacademy.matveenko.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToNewsList implements Command {
	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final Logger log = LogManager.getRootLogger();
			
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession getSession = request.getSession(true);
		List<News> newsList;
		Integer pageNumber = 1;
		Integer pageSize = 5;
		
		try {			
			getSession.setAttribute(AttributsName.PAGE_URL, PageUrl.NEWS_LIST_PAGE);
			newsList = newsService.newsList(pageNumber, pageSize);
			request.setAttribute(AttributsName.NEWS, newsList);
			request.setAttribute(AttributsName.PRESENTATION, AttributsName.NEWS_LIST);			
			request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);
			request.setAttribute(AttributsName.REGISTER_USER, null);
		} catch (ServiceException e) {
			log.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}		
	}
	
	//protected void getNewsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //    String url = WebUtils.getPath(request);
    //    Page<News> page = stuService.findAll(pageNo, pageSize);
    //    page.setPath(url);
    //    request.setAttribute("page", page );
    //    request.getRequestDispatcher("/studentList.jsp").forward(request, response);
   //   }
}