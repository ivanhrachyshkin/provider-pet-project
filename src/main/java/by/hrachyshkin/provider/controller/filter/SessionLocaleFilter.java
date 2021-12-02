package by.hrachyshkin.provider.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class SessionLocaleFilter implements Filter {

    public void init(final FilterConfig arg0) {
    }

    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getParameter("sessionLocale") != null) {
            httpRequest.getSession().setAttribute("lang",
                    httpRequest.getParameter("sessionLocale"));
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
