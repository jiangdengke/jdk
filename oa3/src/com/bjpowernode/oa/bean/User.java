package com.bjpowernode.oa.bean;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class User implements HttpSessionBindingListener {
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        //用户登录了
        // User类型的对象向session中存放了
        //获取ServletCoontext对象
        ServletContext application = event.getSession().getServletContext();
        Object onclinecount = application.getAttribute("onclinecount");
        if(onclinecount == null ){
            application.setAttribute("onclinecount",1);
        }else{
            int count = (Integer)onclinecount;
            count++;
            application.setAttribute("onclinecount",count);
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        //用户退出了
        //User类型的对象从session域中删除了
        ServletContext application = event.getSession().getServletContext();
        Integer onclinecount = (Integer)application.getAttribute("onclinecount");
        onclinecount--;
        application.setAttribute("onclinecount",onclinecount);
    }

    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
