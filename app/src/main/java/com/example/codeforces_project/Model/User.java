package com.example.codeforces_project.Model;

public class User {
    private int id;

    private int groupId;
    private int rating;
    private int maxRating;
    private String name;

    public User(){

    }

    public User(int id, int groupId, int rating, int maxRating, String name) {
        this.id = id;
        this.groupId = groupId;
        this.rating = rating;
        this.maxRating = maxRating;
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(int maxRating) {
        this.maxRating = maxRating;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
