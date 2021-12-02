package by.hrachyshkin.provider.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {

    @Override
    public void init(final FilterConfig fConfig) {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpSession session = httpRequest.getSession(true);

        final String path = httpRequest.getRequestURI()
                .substring(httpRequest.getContextPath().length());

        if (session.getAttribute("accountId") == null
                && session.getAttribute("accountRole") == null
                && !path.startsWith("/login")) {
            httpRequest
                    .getRequestDispatcher("/jsp/login.jsp")
                    .forward(request, response);
        }

        if (path.equals("/")) {
            httpRequest
                    .getRequestDispatcher("/cabinet")
                    .forward(request, response);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
