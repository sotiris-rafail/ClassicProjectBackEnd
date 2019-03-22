package com.classic.project.model.radiboss.exception;

public class RaidBossNotFoundException extends  RuntimeException{
    public RaidBossNotFoundException(String name){
        super("Raid Boss " + name +" not found");
    }
}