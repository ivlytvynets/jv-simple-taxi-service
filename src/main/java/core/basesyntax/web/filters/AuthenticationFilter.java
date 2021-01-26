package core.basesyntax.web.filters;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
    private static final String DRIVER_ID = "driver_id";
    private static final Set<String> ALLOWED_URLS = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ALLOWED_URLS.add("/login");
        ALLOWED_URLS.add("/registration");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String url = req.getServletPath();
        if (ALLOWED_URLS.contains(url)) {
            filterChain.doFilter(req, resp);
            return;
        }
        Long driverId = (Long) req.getSession().getAttribute(DRIVER_ID);
        if (driverId == null) {
            resp.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
