package com.classic.project.model.raidboss;

public enum TypeOfRaidBoss {

    EPIC,
    SIMPLE,
    MINI;


    public static TypeOfRaidBoss getType(String type) {
        for(TypeOfRaidBoss typeOfRaidBoss : TypeOfRaidBoss.values()) {
            if (typeOfRaidBoss.name().trim().toLowerCase().equals(type)) {
                return typeOfRaidBoss;
            }
        }
        return null;
    }
}
