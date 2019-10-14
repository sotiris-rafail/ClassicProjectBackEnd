package com.classic.project.model.raidboss;

public enum RaidBossState {
    ALIVE("Alive"),
    DEAD("Dead"),
    ONWINDOW("On window"),
    AAUNKNOWN("Unknown");

    String state;

    RaidBossState(String state) {
        this.state = state;
    }

    public static String getState(String state) {
        for(RaidBossState raidBossState : RaidBossState.values()){
            if(raidBossState.name().equalsIgnoreCase(state)) {
               return raidBossState.state;
            }
        }
        return state;
    }
}
