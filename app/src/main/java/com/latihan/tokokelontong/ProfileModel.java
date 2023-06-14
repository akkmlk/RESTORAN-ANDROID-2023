package com.latihan.tokokelontong;

public class ProfileModel {
    String name, username, address;

    public ProfileModel(String name, String username, String address) {
        this.name = name;
        this.username = username;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
