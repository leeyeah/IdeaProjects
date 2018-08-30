package com.company;

@FunctionalInterface
public interface Speak {
    default void speak(String lang){
        System.out.println("lang "+lang);
    }

    int Add(int x,int y);


}
