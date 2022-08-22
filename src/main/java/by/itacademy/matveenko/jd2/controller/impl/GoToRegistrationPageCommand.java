package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import by.itacademy.matveenko.jd2.bean.ConnectorStatus;
import by.itacademy.matveenko.jd2.controller.AttributsName;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.controller.JspPageName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToRegistrationPageCommand implements Command {	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession getSession = request.getSession(true);
		request.setAttribute(AttributsName.SHOW_NEWS, AttributsName.DO_NOT_SHOW_NEWS);
		getSession.setAttribute(AttributsName.REGISTER_USER, ConnectorStatus.NOT_REGISTERED);
		request.getRequestDispatcher(JspPageName.BASELAYOUT_PAGE).forward(request, response);		
	}
}