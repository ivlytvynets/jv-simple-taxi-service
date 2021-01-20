package core.basesyntax.controllers.cars;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.service.CarService;
import core.basesyntax.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDriverToCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core.basesyntax");
    private final CarService carService = (CarService) injector
            .getInstance(CarService.class);
    private final DriverService driverService = (DriverService) injector
            .getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cars/addDriverToCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Car car = null;
        Driver driver = null;
        try {
            driver = driverService.get(Long.valueOf(req.getParameter("driver_id")));
            car = carService.get(Long.valueOf(req.getParameter("car_id")));
        } catch (RuntimeException e) {
            req.setAttribute("message", "Car or driver weren't found");
            req.getRequestDispatcher("/WEB-INF/views/cars/addDriverToCar.jsp").forward(req, resp);
        }
        carService.addDriverToCar(driver, car);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
