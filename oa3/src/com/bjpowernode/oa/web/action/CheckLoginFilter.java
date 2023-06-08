package com.bjpowernode.oa.web.action;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckLoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain Chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        HttpSession session =  request.getSession(false);//不新建session对象，获得之前的session对象
        Object username = session.getAttribute("user");
        if(session != null && username!=null){
        Chain.doFilter(request,response);
        }else{
            //跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");//访问web站点的跟即可
        }
    }
}
