package com.classic.project.model.constantParty.file;

import com.classic.project.model.constantParty.ConstantParty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table
public class CpFile {

    @Column
    @Id
    private String fileId;

    @Column
    private String filename;

    @Column
    private String parents;

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

    public CpFile() {
    }

    public CpFile(String fileId, String filename, String parents, FileType fileType, Date creationTime, String webViewLink, String webContentLink, ConstantParty cpImg) {
        this.fileId = fileId;
        this.filename = filename;
        this.parents = parents;
        this.fileType = fileType;
        this.creationTime = creationTime;
        this.webViewLink = webViewLink;
        this.webContentLink = webContentLink;
        this.cpImg = cpImg;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getWebViewLink() {
        return webViewLink;
    }

    public void setWebViewLink(String webViewLink) {
        this.webViewLink = webViewLink;
    }

    public String getWebContentLink() {
        return webContentLink;
    }

    public void setWebContentLink(String webContentLink) {
        this.webContentLink = webContentLink;
    }

    public ConstantParty getCpImg() {
        return cpImg;
    }

    public void setCpImg(ConstantParty cpImg) {
        this.cpImg = cpImg;
    }
}
