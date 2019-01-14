package com.classic.project.model.radiboss;

public class RaidBossExistException extends  RuntimeException{
    public RaidBossExistException(String email){
        super("User " +email +" exist");
    }
}
