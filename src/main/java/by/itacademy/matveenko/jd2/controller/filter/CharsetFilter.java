package by.itacademy.matveenko.jd2.controller.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class CharsetFilter implements Filter {
	private String encoding;
	private ServletContext context;
	private static final String CHARACTER_ENCODING = "characterEncoding";
	private static final String CHARSET_WAS_SET = "Charset was set!";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter(CHARACTER_ENCODING);
		context = filterConfig.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		context.log(CHARSET_WAS_SET);	
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
	}
}