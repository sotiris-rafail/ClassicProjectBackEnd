package com.classic.project.model.constantParty.file;

import com.classic.project.model.constantParty.ConstantParty;
import com.classic.project.model.constantParty.file.parentFile.ParentFile;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class CpFile {

    @Column
    @Id
    private String fileId;

    @Column
    private String filename;

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

    @OneToMany(mappedBy = "folderId", cascade = CascadeType.ALL)
    private List<ParentFile> parents = new ArrayList<>();

    public CpFile() {
    }

    public CpFile(String fileId, String filename, List<ParentFile> parents, FileType fileType, Date creationTime, String webViewLink, String webContentLink, ConstantParty cpImg) {
        this.fileId = fileId;
        this.filename = filename;
        this.parents = parents;
        this.fileType = fileType;
        this.creationTime = creationTime;
        this.webViewLink = webViewLink;
        this.webContentLink = webContentLink;
        this.cpImg = cpImg;
    }

    public CpFile(String fileId, String filename, FileType fileType, Date creationTime, String webViewLink, String webContentLink, ConstantParty cpImg) {
        this.fileId = fileId;
        this.filename = filename;
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

    @JsonIgnore
    public List<ParentFile> getParents() {
        return parents;
    }

    public void setParents(List<ParentFile> parents) {
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
