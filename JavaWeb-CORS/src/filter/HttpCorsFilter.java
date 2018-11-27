package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpCorsFilter implements Filter {
    String[] allowOriginArray = null;

    @Override
    public void init(FilterConfig filterConfig) {
        //获取web.xml中的数据
        String initParameter = filterConfig.getInitParameter("allow-Origin");
        //
        if (initParameter != null) {
            if (initParameter.equals("*")) {
                //如果是星号直接添加到数组中
                allowOriginArray = new String[]{"*"};
            } else {
                //不是星号直接分割，然后添加到数组中
                allowOriginArray = initParameter.split(",");
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //定义一个boolean类型变量
        boolean isOrigin = false;
        //如果origin字段存在，allowOriginArray具有 * 或者具有 origin；然后增加响应字段
        String origin = req.getHeader("Origin");
        if (origin != null && !origin.isEmpty()) {

            for (String s : allowOriginArray) {
                //
                if (s.equals("*") || s.equals(origin)) {
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
