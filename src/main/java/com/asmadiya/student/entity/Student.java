package com.asmadiya.student.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long srno; // Serial Number (Primary Key)

    private String name;
    private String state;
    private double percentage;
    private int yop; // Year of Passing

    public Student() {
    }

    public Student(String name, String state, double percentage, int yop) {
        this.name = name;
        this.state = state;
        this.percentage = percentage;
        this.yop = yop;
    }

    public Long getSrno() {
        return srno;
    }

    public void setSrno(Long srno) {
        this.srno = srno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getYop() {
        return yop;
    }

    public void setYop(int yop) {
        this.yop = yop;
    }
}
