package com.company;

@FunctionalInterface
public interface PersonFactory<T extends Person> {
    T create(String fname,String lname);
}
