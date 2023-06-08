package com.bjpowernode.oa.user;

import com.bjpowernode.oa.bean.User;
import com.bjpowernode.oa.utils.DButils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import jakarta.servlet.http.*;

@WebServlet({"/user/login","/user/exit"})

public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
        if("/user/login".equals(servletPath)){
            try {
                doLogin(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }else if("/user/exit".equals(servletPath)){
            doExit(request,response);
        }
    }

    private void doExit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取session对象
        HttpSession session =  request.getSession(false);
        //从session域中移除session对象
        session.removeAttribute("user");
        //手动销毁session对象
        session.invalidate();
        //跳转到登录页面
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //做一个什么事呢？
        //获得用户输入的用户名密码。
        //连接数据库，与数据库中存的数据进行比对
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //连接数据库
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DButils.getConnection();
            String sql = "select * from t_user where username=? and password=?";
            ps=conn.prepareStatement(sql);
            //给问号赋值
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();
            //这里不用while因为查出来最多一条数据
            if(rs.next()){
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DButils.close(conn,ps,rs);
        }
        if(success){
            //登录成功，跳转到list页面
            //获取session
            HttpSession session = (HttpSession) request.getSession();
            //往session域里面存username
//            session.setAttribute("username",username);
            User user = new User(username,password);
            session.setAttribute("user",user);
            //登录成功了，实现"10天免登录功能"
            String f = request.getParameter("f");
            if("1".equals(f)){
                //将输入的username和password都存到cookie当中
                //创建2个cookie对象吗，一个存用户名，一个存密码
                Cookie cookie1 = new Cookie("username",username);
                Cookie cookie2 = new Cookie("password",password);
                //设置”10天内免登录“
                cookie1.setMaxAge(60*60*24*10);
                cookie2.setMaxAge(60*60*24*10);
                //设置访问的根路径
                cookie1.setPath(request.getContextPath());
                cookie2.setPath(request.getContextPath());
                //响应给浏览器
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }else{
            //登录失败，跳转到error页面
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }
    }
}
