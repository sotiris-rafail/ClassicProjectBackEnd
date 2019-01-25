package com.classic.project.model.clan;


import com.classic.project.model.character.Character;
import com.classic.project.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Clan {

    @Id
    @GeneratedValue
    @Column
    private int clanId;
    @Column
    private String name;
    @OneToMany(mappedBy = "clan", cascade = CascadeType.ALL)
    private List<Character> clanMembers;

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

    public List<Character> getClanMembers() {
        return clanMembers;
    }

    public void setClanMembers(List<Character> clanMembers) {
        this.clanMembers = clanMembers;
    }
}