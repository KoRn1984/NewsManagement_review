package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import by.itacademy.matveenko.jd2.bean.ConnectorStatus;
import by.itacademy.matveenko.jd2.controller.AttributsName;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.controller.JspPageName;
import by.itacademy.matveenko.jd2.controller.NewsParameterName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToEditNewsPage implements Command {	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession getSession = request.getSession(true);		
		getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);		
		getSession.setAttribute(AttributsName.NEWS_COMMANDS_NAME, AttributsName.EDIT_NEWS);
		getSession.setAttribute(AttributsName.NEWS_ID, request.getParameter(NewsParameterName.JSP_ID_NEWS));
		request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);		
	}
}