package com.bjpowernode.oa.bean;

import java.util.Objects;

/**
 * 一个普通的Java类，这个Java类可以封装零散的数据。代表了一个部门对象
 */

public class Dept {
    private String deptno;
    private String dname;
    private String loc;

    public Dept() {
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptno='" + deptno + '\'' +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dept)) return false;
        Dept dept = (Dept) o;
        return Objects.equals(getDeptno(), dept.getDeptno()) &&
                Objects.equals(getDname(), dept.getDname()) &&
                Objects.equals(getLoc(), dept.getLoc());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeptno(), getDname(), getLoc());
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
