package com.example.kotlinDemo.java.dataClass;

import org.jetbrains.annotations.NotNull;

public class User {

    @NotNull
    private final Integer userId;
    @NotNull
    private final Name name;
    @NotNull
    private final Integer age;

    public User(@NotNull Integer userId,
                @NotNull Name name,
                @NotNull Integer age) {
        this.userId = userId;
        this.name = name;
        this.age = age;
    }

    @NotNull
    public Integer getUserId() {
        return userId;
    }

    @NotNull
    public Name getName() {
        return name;
    }

    @NotNull
    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name=" + name +
                ", age=" + age +
                '}';
    }
}
