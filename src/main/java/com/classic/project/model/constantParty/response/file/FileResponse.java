package com.classic.project.model.constantParty.response.file;

import java.util.Date;
import java.util.List;

public class FileResponse {

    private String fileId;
    private String name;
    private List<String> parent;
    private String type;
    private Date creationTime;
    private String webViewLink;
    private String webContentLink;

    public FileResponse(String fileId, String name, List<String> parent, String type) {
        this.fileId = fileId;
        this.name = name;
        this.parent = parent;
        this.type = type;
    }

    public FileResponse(String fileId, String name, List<String> parent, String type, Date creationTime, String webViewLink, String webContentLink) {
        this.fileId = fileId;
        this.name = name;
        this.parent = parent;
        this.type = type;
        this.creationTime = creationTime;
        this.webViewLink = webViewLink;
        this.webContentLink = webContentLink;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParent() {
        return parent;
    }

    public void setParent(List<String> parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
