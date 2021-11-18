package by.hrachyshkin.provider.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class SessionLocaleFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getParameter("sessionLocale") != null) {
            httpRequest.getSession().setAttribute("lang", httpRequest.getParameter("sessionLocale"));
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}
