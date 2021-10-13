package com.example.kotlinDemo.java.classes;

public class Example {

    public static void main(String[] args){

        Person person = new Person("Jan", "Janek", false);

        System.out.println("person = " + person);



       User user1 = new User(123, new Name("Piotr", "Nowak"), 33);

        System.out.println("User: " + user1);

        System.out.println("User1 id = " + user1.getUserId());
    }
}
