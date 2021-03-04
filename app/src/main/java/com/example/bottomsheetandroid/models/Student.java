package com.example.bottomsheetandroid.models;

public class Student {
    private int ID;
    private String NameStudent;

    public Student(int ID, String nameStudent) {
        this.ID = ID;
        NameStudent = nameStudent;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameStudent() {
        return NameStudent;
    }

    public void setNameStudent(String nameStudent) {
        NameStudent = nameStudent;
    }
}
