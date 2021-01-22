package core.basesyntax.controller.driver;

import core.basesyntax.exception.AuthenticationException;
import core.basesyntax.lib.Injector;
import core.basesyntax.model.Driver;
import core.basesyntax.security.AuthenticationService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core.basesyntax");
    private AuthenticationService authenticationService = (AuthenticationService) injector
            .getInstance(AuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/driver/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            Driver driver = authenticationService.login(login, password);
            HttpSession session = req.getSession();
            session.setAttribute("driver_id", driver.getId());
        } catch (AuthenticationException e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/view/driver/login.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}