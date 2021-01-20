package core.basesyntax.controllers.drivers;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Driver;
import core.basesyntax.service.DriverService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllDriversController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core.basesyntax");
    private DriverService driverService = (DriverService) injector
            .getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        List<Driver> drivers = driverService.getAll();
        req.setAttribute("drivers",drivers);
        req.getRequestDispatcher("/WEB-INF/views/drivers/all.jsp").forward(req, resp);
    }
}
