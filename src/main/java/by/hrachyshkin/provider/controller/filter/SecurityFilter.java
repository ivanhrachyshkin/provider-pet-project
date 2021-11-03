//package by.hrachyshkin.provider.controller.filter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter(urlPatterns = "/*")
//public class SecurityFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig fConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        httpRequest.getSession(true);
//
//        if (httpRequest.getAttribute("accountId") == null
//        &&httpRequest.getAttribute("accountRole") == null) {
//
//            request.getRequestDispatcher("/login.jsp").forward(request, response);
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//    }
//}
