package core.basesyntax.security.impl;

import core.basesyntax.exception.AuthenticationException;
import core.basesyntax.lib.Inject;
import core.basesyntax.lib.Service;
import core.basesyntax.model.Driver;
import core.basesyntax.security.AuthenticationService;
import core.basesyntax.service.DriverService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Driver driver = driverService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException("Driver's login or password is incorrect"));
        if (driver.getPassword().equals(password)) {
            return driver;
        }
        throw new AuthenticationException("Driver's login or password is incorrect");
    }
}
