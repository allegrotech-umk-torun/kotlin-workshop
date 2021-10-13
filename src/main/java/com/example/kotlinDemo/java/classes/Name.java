package com.example.kotlinDemo.java.classes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Name {

    @NotNull
    private final String firstName;
    @Nullable
    private final String lastName;

    public Name(@NotNull String firstName, @Nullable String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

//    @Override
//    public String toString() {
//        return "Name{" +
//                "firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                '}';
//    }
}




