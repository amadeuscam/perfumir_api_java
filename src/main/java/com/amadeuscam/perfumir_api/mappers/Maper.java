package com.amadeuscam.perfumir_api.mappers;

public interface Maper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);
}

