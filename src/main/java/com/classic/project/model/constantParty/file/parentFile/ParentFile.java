package com.classic.project.model.constantParty.file.parentFile;

import com.classic.project.model.constantParty.file.CpFile;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@IdClass(ParentFileId.class)
public class ParentFile {

    private String fileId;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fileId", referencedColumnName = "fileId", insertable = false, updatable = false)
    private CpFile folderId;

    @Id
    @Column
    private String parentId;

    @Column
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date registrationDate;

    public ParentFile(CpFile folderId, String parentId) {
        this.folderId = folderId;
        this.parentId = parentId;
    }

    public ParentFile(CpFile folderId, String parentId, String fileId) {
        this.folderId = folderId;
        this.parentId = parentId;
        this.fileId = fileId;
    }

    public ParentFile(String parentId) {
        this.parentId = parentId;
    }

    public ParentFile() {
    }


    public CpFile getFolderId() {
        return folderId;
    }

    public void setFolderId(CpFile folderId) {
        this.folderId = folderId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
