package com.example.kotlinDemo.java.functions;


import static com.example.kotlinDemo.kotlin.functions.FunctionsKt.sum1kt;

public class Example {

    public static void main(String[] args) {

        System.out.println("sum1kt(3, 4) = " + sum1kt(3, 4));

        Functions functions = new Functions();

        System.out.println("sum1(5, 4) = " + functions.sum1(5, 4));

    }

}
