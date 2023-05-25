package com.sith.birthdayreminder.home;

public class ProfileModel {
    String name, dob, zodiacSign, age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getZodiacSign() {
        return zodiacSign;
    }

    public void setZodiacSign(String zodiacSign) {
        this.zodiacSign = zodiacSign;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public ProfileModel(String name, String dob, String zodiacSign, String age) {
        this.name = name;
        this.dob = dob;
        this.zodiacSign = zodiacSign;
        this.age = age;
    }
}
