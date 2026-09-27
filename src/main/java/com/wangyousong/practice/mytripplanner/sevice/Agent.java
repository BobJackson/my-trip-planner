package com.wangyousong.practice.mytripplanner.sevice;

public interface Agent<I, O> {
    String name();

    O execute(I input);
}
