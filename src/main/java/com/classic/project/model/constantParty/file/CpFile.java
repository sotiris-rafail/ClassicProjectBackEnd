package com.classic.project.model.constantParty.file;

import com.classic.project.model.constantParty.ConstantParty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table
public class CpFile {

    @Column
    @Id
    private String fileId;

    @Column
    private String filename;

    @Column
    private String parentId;

    @Column
    private FileType fileType;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date creationTime;

    @Column
    private String webViewLink;

    @Column
    private String webContentLink;

    @ManyToOne
    @JoinColumn(name = "cpId")
    private ConstantParty cpImg;
}
