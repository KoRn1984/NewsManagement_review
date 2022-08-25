package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import by.itacademy.matveenko.jd2.bean.ConnectorStatus;
import by.itacademy.matveenko.jd2.controller.AttributsName;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.controller.JspPageName;
import by.itacademy.matveenko.jd2.controller.PageUrl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToAddNewsPage implements Command {	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession getSession = request.getSession(true);		
		getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);		
		getSession.setAttribute(AttributsName.NEWS_COMMANDS_NAME, AttributsName.ADD_NEWS);
		request.getSession(true).setAttribute(AttributsName.LOCAL, request.getParameter(AttributsName.LOCAL));
		request.getSession(true).setAttribute(AttributsName.PAGE_URL, PageUrl.ADD_NEWS_PAGE);
		request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);
	}
}