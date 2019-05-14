package com.classic.project.model.constantParty.file;

import javax.persistence.*;

@Entity
@Table
@IdClass(ParentFileId.class)
public class ParentFile {

    private String fileId;

    @Id
    @ManyToOne
    @JoinColumn(name = "fileId", referencedColumnName = "fileId", insertable = false, updatable = false)
    private CpFile folderId;

    @Id
    @Column
    private String parentId;

    public CpFile getFolderId() {
        return folderId;
    }

    public ParentFile(CpFile folderId, String parentId) {
        this.folderId = folderId;
        this.parentId = parentId;
    }

    public ParentFile(CpFile folderId, String parentId, String fileId) {
        this.folderId = folderId;
        this.parentId = parentId;
        this.fileId = fileId;
    }

    public ParentFile() {
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
}
