package com.company;

public interface Converter<F,T> {
    T convert(F o);
}
