package com.classic.project.model.user.response;

public class AddUserToCP {
    private int cpId;
    private int[] usersToUpdate;

    public int getCpId() {
	return cpId;
    }

    public void setCpId(int cpId) {
	this.cpId = cpId;
    }

    public int[] getUsersToUpdate() {
	return usersToUpdate;
    }

    public void setUsersToUpdate(int[] usersToUpdate) {
	this.usersToUpdate = usersToUpdate;
    }
}
