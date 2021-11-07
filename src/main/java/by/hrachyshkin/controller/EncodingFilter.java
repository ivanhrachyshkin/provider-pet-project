package by.hrachyshkin.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.setCharacterEncoding("UTF-8");

		httpResponse.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
		httpResponse.setHeader("Pragma", "no-cache"); //HTTP 1.0
		httpResponse.setDateHeader("Expires", 0); //prevents caching at the proxy server

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}
}
