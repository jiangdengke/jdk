package com.bjpowernode.oa.web.action;

import com.bjpowernode.oa.bean.Dept;
import com.bjpowernode.oa.utils.DButils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/dept/list","/dept/detail","/dept/delete","/dept/save","/dept/modfiy"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if("/dept/list".equals(servletPath)){
                doList(request,response);
            }else if("/dept/detail".equals(servletPath)){
                doDetail(request,response);
            }else if("/dept/delete".equals(servletPath)){
                doDel(request,response);
            }else if("/dept/save".equals(servletPath)){
                doSave(request,response);
            }else if("/dept/modfiy".equals(servletPath)){
                doModfiy(request,response);
            }

    }
    private void doModfiy(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //设置请求体的字符编码
        request.setCharacterEncoding("UTF-8");
        String contextPath = request.getContextPath();
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DButils.getConnection();
            String sql = "update dept set dname = ? , loc = ? where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,dname);
            ps.setString(2,loc);
            ps.setString(3,deptno);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DButils.close(conn,ps,null);
        }
        if(count==1){
            //更新成功
//            request.getRequestDispatcher("/dept/list").forward(request,response);
            //使用重定向
            response.sendRedirect(contextPath+"/dept/list");
        }
    }
    private void doSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");//解决页面上的乱码问题
        String contextPath = request.getContextPath();
        //获取部门信息
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        //连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DButils.getConnection();
            String sql = "insert into dept(deptno,dname,loc) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            ps.setString(2,dname);
            ps.setString(3,loc);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DButils.close(conn,ps,null);
        }
        if(count==1){
            response.sendRedirect(request.getContextPath()+"/dept/list");
        }
    }
    private void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取部门编号
        String deptno = request.getParameter("deptno");
        //连接数据库，删除数据
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DButils.getConnection();
            String sql = "delete from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DButils.close(conn,ps,null);
        }
        if(count == 1){
            //删除成功
            //使用重定向
//            response.sendRedirect("/oa/dept/list");
            //获取项目的根路径
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath+"/dept/list");
        }
    }

    /**
     * 根据部门编号获取部门信息
     * @param request
     * @param response
     */
    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //封装对象
        Dept dept  = new Dept();
        //获取部门编号
        String dno = request.getParameter("dno");
        //获取的是一个信息，相当于一个咖啡豆，因此不用袋子装起来。
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DButils.getConnection();
            String sql = "select dname,loc from dept where deptno =? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,dno);
            rs = ps.executeQuery();
            if(rs.next()){
                //查询到需要输出的三种信息
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                //封装成对象，并给对象赋值
                dept.setDname(dname);
                dept.setLoc(loc);
                dept.setDeptno(dno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DButils.close(conn,ps,rs);
        }
        //将对象存到request域当中
        request.setAttribute("dept",dept);
        //转发（不能使用重定向）
//        request.getRequestDispatcher("/detail.jsp").forward(request,response);
        String f = request.getParameter("f");
        if("m".equals(f)){
            //跳转到修改页面
            request.getRequestDispatcher("/edit.jsp").forward(request,response);
        }else if("d".equals(f)){
            //跳转到详请页面
            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        }
    }

    /**
     * 连接数据库，查询所有的部门信息，将部门信息收集好，然后跳转到JSP做页面展示
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Dept> depts = new ArrayList<>();
        try {
            conn = DButils.getConnection();
            String sql = "select deptno, dname ,loc from dept";
            ps = conn.prepareStatement(sql);
            rs  = ps.executeQuery();
            while(rs.next()){
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                Dept dept = new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);
                depts.add(dept);
                request.setAttribute("deptlist",depts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DButils.close(conn,ps,rs);
        }
        //将一个集合放到请求域当中
        request.setAttribute("deptlist",depts);
        //转发，不要重定向，因为用到了request域，必须是一次请求
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}

