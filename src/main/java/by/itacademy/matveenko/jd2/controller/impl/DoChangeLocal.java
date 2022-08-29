package by.itacademy.matveenko.jd2.controller.impl;

import java.io.IOException;

import by.itacademy.matveenko.jd2.controller.AttributsName;
import by.itacademy.matveenko.jd2.controller.Command;
import by.itacademy.matveenko.jd2.controller.PageUrl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoChangeLocal implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String local = request.getParameter(AttributsName.LOCAL);
		HttpSession getSession = request.getSession(true);
		getSession.setAttribute(AttributsName.LOCAL, local);
		String url = (String) getSession.getAttribute(AttributsName.PAGE_URL);
		StringBuilder urlForRedirect = new StringBuilder(url);
		urlForRedirect.append(PageUrl.AMPERSAND_LOCAL);
		urlForRedirect.append(local);
		response.sendRedirect(urlForRedirect.toString());
	}
}