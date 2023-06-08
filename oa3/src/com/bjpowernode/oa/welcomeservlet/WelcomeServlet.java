package com.bjpowernode.oa.welcomeservlet;

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

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取cookie对象
        Cookie[] cookies = request.getCookies();
        String username = null;
        String password = null;
        if(cookies != null){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if("username".equals(name)){
                   username = cookie.getValue();
                }else if("password".equals(name)){
                   password = cookie.getValue();
                }
            }
        }
        if(username != null && password != null){
            //连接数据库，进行验证
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            boolean success = false;
            try {
                conn = DButils.getConnection();
                String sql = "select * from t_user where username=? and password = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1,username);
                ps.setString(2,password);
                rs = ps.executeQuery();
                if(rs.next()){
                    //登录成功
                    success = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                DButils.close(conn,ps,rs);
            }
            if(success){
                //登录成功
                //获取session
                HttpSession session = request.getSession();
                //往session域里面存username
                User user = new User(username,password);
                session.setAttribute("user",user);
                //正确表示登录成功
                //跳转到部门列表页面
                response.sendRedirect(request.getContextPath()+"/dept/list");
            }else{
                //错误返回登录页面
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        }else{
            //跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
    }
}
