package com.tp.wechat.Filter;


import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;

public class UrlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
          BufferedReader br = request.getReader();

    }

    @Override
    public void destroy() {

    }
}
