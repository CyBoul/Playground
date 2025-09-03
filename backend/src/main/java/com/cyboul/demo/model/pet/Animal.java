package com.cyboul.demo.model.pet;

public enum Animal {
    UNKNOWN, CAT, DOG, WOLF, TIGER, PANDA, EAGLE, RACOON;
    public String toString(){
        return this.name();
    }
}
