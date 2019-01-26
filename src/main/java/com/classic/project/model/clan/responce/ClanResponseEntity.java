package com.classic.project.model.clan.responce;

import com.classic.project.model.character.responce.ResponseCharacter;
import com.classic.project.model.clan.Clan;

import java.util.ArrayList;
import java.util.List;

public class ClanResponseEntity {

    private int clanId;
    private String name;
    private int numberOfMembers;
    private List<ResponseCharacter> members;

    public ClanResponseEntity(String name, int numberOfMembers, List<ResponseCharacter> members) {
        this.name = name;
        this.numberOfMembers = numberOfMembers;
        this.members = members;
    }

    public ClanResponseEntity(int clanId, String name) {
	this.clanId = clanId;
	this.name = name;
    }

    public int getClanId() {
	return clanId;
    }

    public void setClanId(int clanId) {
	this.clanId = clanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public List<ResponseCharacter> getMembers() {
        return members;
    }

    public void setMembers(List<ResponseCharacter> members) {
        this.members = members;
    }

    public static List<ClanResponseEntity> convertAll(List<Clan> all) {
        List<ClanResponseEntity> response = new ArrayList<>();
        all.forEach(clan -> response.add(new ClanResponseEntity(clan.getName(), clan.getClanMembers().size(), ResponseCharacter.convert(clan.getClanMembers()))));
        return response;
    }

    public static List<ClanResponseEntity> convertForCharRegister(List<Clan> all) {
	List<ClanResponseEntity> response = new ArrayList<>();
	all.forEach(clan -> response.add(new ClanResponseEntity(clan.getClanId(), clan.getName())));
	return response;
    }
}
