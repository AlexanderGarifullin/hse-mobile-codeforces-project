package com.example.codeforces_project.Model;

public class Group {

    private int id;
    private String name;

    public int getId() {
        return id;
    }
    public Group(){

    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
