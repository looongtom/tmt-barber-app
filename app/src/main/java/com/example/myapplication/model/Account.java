package com.example.myapplication.model;

import java.io.Serializable;

public class Account implements Serializable {

    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String gender;
    private String avatar = "https://res.cloudinary.com/dgm68hajt/image/upload/v1689830191/user_ppwwwc.png";
    private boolean is_Block;
    private int roleId = 3;
    private String about;

    public boolean isIs_Block() {
        return is_Block;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    //Staff
    public Account(int id, String name, String username, String password, String email, String phone, String dateOfBirth, String gender,String avatar, int roleId,String about) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.avatar = avatar;
        this.roleId = roleId;
        this.about = about;
    }

    //User
    public Account(int id, String username, String password, String email, String phone, String dateOfBirth, String gender, int roleId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.roleId = roleId;
    }

    public Account(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Account() {
    }

    public Account(int id, String name, String username, String password, String email, String phone, String dateOfBirth, String gender,String avatar, boolean is_block, int roleId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.avatar = avatar;
        this.roleId = roleId;
        this.is_Block = is_block;
    }

    //toString


    @Override
    public String toString() {
        return this.name;
    }

    public String getAccount() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", avatar='" + avatar + '\'' +
                ", is_Block=" + is_Block +
                ", roleId=" + roleId +
                ", about me=" + about +
                '}';
    }

    //Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean getIs_Block() {
        return is_Block;
    }

    public void setIs_Block(boolean is_Block) {
        this.is_Block = is_Block;
    }
}
