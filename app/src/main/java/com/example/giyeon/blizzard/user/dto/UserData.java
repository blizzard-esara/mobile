package com.example.giyeon.blizzard.user.dto;

public class UserData {

    private String id;
    private String phone;
    private String gender;
    private String age;
    private int money;
    private String date;
    private String email;



    private UserData() {

    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private static class LazyHolder {
        public static final UserData INSTANCE = new UserData();
    }
    public static UserData getInstance() {
        return LazyHolder.INSTANCE;
    }


}
