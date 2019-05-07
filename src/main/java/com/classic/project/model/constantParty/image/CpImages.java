package com.classic.project.model.constantParty.image;

import com.classic.project.model.constantParty.ConstantParty;

import javax.persistence.*;

@Entity
@Table
public class CpImages {

    @Column
    @Id
    private String imageId;

    @Column
    private String imageName;

    @ManyToOne
    @JoinColumn(name = "cpId")
    private ConstantParty cpImg;

}
