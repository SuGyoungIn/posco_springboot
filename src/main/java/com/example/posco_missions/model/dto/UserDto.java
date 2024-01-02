package com.example.posco_missions.model.dto;

public class UserDto {
    private  String id;
    private String pw;
    private int point;
    private int admin;

    public UserDto() {

    }
    public UserDto(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public int getPoint() {
        return point;
    }

    public int getAdmin() {
        return admin;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPoint(int point) {
        this.point = point;
    }

}
