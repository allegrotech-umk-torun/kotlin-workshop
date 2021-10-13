package com.example.kotlinDemo.java.classes;

public class Person {
    private final String name;
    private String nickName;
    private Boolean isMarried;
    private final String upperCaseName;

    public Person(String name, String nickName, Boolean isMarried) {
        this.name = name;
        this.nickName = nickName;
        this.isMarried = isMarried;
        this.upperCaseName = name.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getMarried() {
        return isMarried;
    }

    public void setMarried(Boolean married) {
        isMarried = married;
    }

    public String getUpperCaseName() {
        return upperCaseName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", isMarried=" + isMarried +
                ", upperCaseName='" + upperCaseName + '\'' +
                '}';
    }
}
