package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*@WebFilter("/*")*/
public class CorsFilter implements Filter {
     FilterConfig config;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String[] allowList = new String[]{
                "http://127.0.0.1:8081",
                "http://192.168.1.178:8081"
        };

        String origin = req.getHeader("Origin");

        boolean isOrigin = false;
        if (origin != null && !origin.isEmpty()) {
            for (String s : allowList) {
                if (origin.equals(s)) {
                    isOrigin = true;
                    break;
                }
            }

            if (isOrigin) {
                resp.setHeader("Access-Control-Allow-Origin", origin);
            }
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
