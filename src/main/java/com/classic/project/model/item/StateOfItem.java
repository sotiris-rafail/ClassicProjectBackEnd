package com.classic.project.model.item;

import javassist.NotFoundException;

public enum StateOfItem {
    SOLD("Sold"),
    UNSOLD("Un Sold");

    private String state;
    StateOfItem(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static StateOfItem getValueByState(String value) {
        for(StateOfItem state : values()){
            if(state.state.equals(value)){
                return state;
            }
        }
        return null;
    }
}
