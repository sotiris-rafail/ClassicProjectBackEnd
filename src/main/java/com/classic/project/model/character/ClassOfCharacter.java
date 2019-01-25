package com.classic.project.model.character;

public enum ClassOfCharacter {

    ES("EVA'S Saint");

    private String className;
    ClassOfCharacter(String className){
        this.className = className;
    }

    public String getName() {
        return this.className;
    }

    public void setName(String name){
        this.className = name;
    }
}
