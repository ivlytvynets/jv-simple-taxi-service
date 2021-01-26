package core.basesyntax.security;

import core.basesyntax.exception.AuthenticationException;
import core.basesyntax.model.Driver;

public interface AuthenticationService {
    Driver login(String login, String password) throws AuthenticationException;
}
