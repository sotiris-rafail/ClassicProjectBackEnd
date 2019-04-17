package com.classic.project.model.item;

public enum Grade {

    A("A"),
    B("B"),
    C("C"),
    D("D"),
    NONE("NONE");


    private String grade;
    Grade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
