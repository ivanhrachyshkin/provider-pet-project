//package by.hrachyshkin.provider;
//
//import by.hrachyshkin.provider.controller.action.Action;
//import by.hrachyshkin.provider.controller.action.action_factory.ActionFactory;
//import lombok.SneakyThrows;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//@WebServlet("/langs")
//public class Localization extends HttpServlet {
//
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//
//    @SneakyThrows
//    private void processRequest(HttpServletRequest request,
//                                HttpServletResponse response)
//            throws ServletException, IOException {
//
//       Locale enLocale = new Locale("en", "US");
//       Locale ruLocale = new Locale("ru", "RU");
//
//        Locale locale = Language.valueOf(request.getParameter(“language”)).getLocale();
//        ResourceBundle rb = ResourceBundle.g
//    }
//}
