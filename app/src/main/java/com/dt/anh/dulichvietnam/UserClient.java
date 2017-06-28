package com.dt.anh.dulichvietnam;

/**
 * Created by ALIENWARE on 4/21/2017.
 */

public class UserClient {
    private String idUserClient;
    private String firtsName;
    private String lastName;
    private String email;
    private String grade;
    private String address;
    private String username;
    private String password;
    private String imgCover;
    private String imgAvatar;

    public UserClient() {

    }

    public UserClient(String idUserClient,
                      String firtsName, String lastName, String email,
                      String grade, String address, String username,
                      String password, String imgCover, String imgAvatar) {
        this.idUserClient = idUserClient;
        this.firtsName = firtsName;
        this.lastName = lastName;
        this.email = email;
        this.grade = grade;
        this.address = address;
        this.username = username;
        this.password = password;
        this.imgCover = imgCover;
        this.imgAvatar = imgAvatar;
    }


    public String getImgCover() {
        return imgCover;
    }

    public void setImgCover(String imgCover) {
        this.imgCover = imgCover;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdUserClient() {
        return idUserClient;
    }

    public void setIdUserClient(String idUserClient) {
        this.idUserClient = idUserClient;
    }

    public String getFirtsName() {
        return firtsName;
    }

    public void setFirtsName(String firtsName) {
        this.firtsName = firtsName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
