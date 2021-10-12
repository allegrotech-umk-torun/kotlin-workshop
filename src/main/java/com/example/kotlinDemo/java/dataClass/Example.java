package com.example.kotlinDemo.java.dataClass;

public class Example {

    public static void main(String[] args){

       User user1 = new User(123, new Name("Piotr", "Nowak"), 33);

        System.out.println("User: " + user1);

        System.out.println("User1 id = " + user1.getUserId());
    }
}
