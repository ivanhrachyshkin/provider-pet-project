package by.hrachyshkin.servlet;

import by.hrachyshkin.dao.DAOFactory;
import by.hrachyshkin.dao.UserDAO;
import by.hrachyshkin.entity.User;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private final UserDAO userDAO = DAOFactory.INSTANCE.getUserDAO();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user1 = new User("login", "password", "name",
                "surname", "email", "passport");

        User user2 = new User("login2", "password2", "name2",
                "surname2", "email2", "passport2");

        User user3 = new User("login3", "password3", "name3",
                "surname3", "email3", "passport3");

        User user4 = new User("login4", "password4", "name4",
                "surname4", "email4", "passport4");

        userDAO.create(user1);
        userDAO.create(user3);
        userDAO.create(user2);
        userDAO.create(user4);

//        List<User> all = userDAO.findAll();
//        resp.getOutputStream().println(all.toString());
//
//        User user = userDAO.findOneByLogin("login2");
//        resp.getOutputStream().println(user.toString());
//
//        List<User> allSurnameSorted = userDAO.findAllSortedBySurname();
//        resp.getOutputStream().println(allSurnameSorted.toString());
    }
}
