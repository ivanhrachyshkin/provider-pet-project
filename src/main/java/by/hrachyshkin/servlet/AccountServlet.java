package by.hrachyshkin.servlet;

import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountServlet extends HttpServlet {



    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
// User user1 = new User("login", "password", new UsersInfo("aname",
//         "surname", "email", "passport"));
//
// User user2 = new User("login2", "password2", new UsersInfo("dname2",
//         "surname2", "email2", "passport2"));
//
// User user3 = new User("login3", "password3", new UsersInfo("came3",
//         "surname3", "email3", "passport3"));
//
// User user4 = new User("login4", "password4", new UsersInfo("bame4",
//         "surname4", "email4", "passport4"));
//
// userDAO.create(user1);
// userDAO.create(user3);
// userDAO.create(user2);
// userDAO.create(user4);
//


//
// final Account account = userDAOImpl.findOneByLogin("login3");
// resp.getOutputStream().println(account.toString());
//
// final List<Account> allSurnameSorted = userDAOImpl.findAllSortedByName();
// resp.getOutputStream().println(allSurnameSorted.toString());
    }
}
