package com.lmhu.firstcodebooksource.chapter6.broadcastbestpractice.filepersistence;

/**
 * Created by hulimin on 2017/3/13.
 */

public class StudentBean {
    private String sno;
    private String name;
    private String sex;
    private int age;

    public StudentBean(String sno, String name, String sex, int age) {
        this.sno = sno;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public StudentBean() {
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
