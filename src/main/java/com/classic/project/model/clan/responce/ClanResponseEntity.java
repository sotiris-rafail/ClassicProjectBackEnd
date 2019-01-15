package com.classic.project.model.clan.responce;

import com.classic.project.model.character.responce.ResponseCharacter;
import com.classic.project.model.clan.Clan;

import java.util.ArrayList;
import java.util.List;

public class ClanResponseEntity {

    private String name;
    private int numberOfMembers;
    private List<ResponseCharacter> member;

    public ClanResponseEntity(String name, int numberOfMembers, List<ResponseCharacter> member) {
        this.name = name;
        this.numberOfMembers = numberOfMembers;
        this.member = member;
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

    public List<ResponseCharacter> getMember() {
        return member;
    }

    public void setMember(List<ResponseCharacter> member) {
        this.member = member;
    }

    public static List<ClanResponseEntity> convertAll(List<Clan> all) {
        List<ClanResponseEntity> responce = new ArrayList<>();
        all.forEach(clan -> responce.add(new ClanResponseEntity(clan.getName(), clan.getClanMembers().size(), ResponseCharacter.convert(clan.getClanMembers()))));
        return responce;
    }
}
