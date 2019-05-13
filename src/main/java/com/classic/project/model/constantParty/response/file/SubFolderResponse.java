package com.classic.project.model.constantParty.response.file;

import javax.persistence.Column;

import java.util.*;

public class SubFolderResponse {

    private String folderId;
    private String name;
    private List<String> parent;
    private String type;
    private Date creationTime;
    private String webViewLink;
    private String webContentLink;

    private List<SubFolderResponse> folderResponseMap =new ArrayList<SubFolderResponse>();
    private List<FileResponse> fileResponseMap = new ArrayList<FileResponse>();

    public SubFolderResponse(String folderId, String name, List<String> parent, String type) {
        this.folderId = folderId;
        this.name = name;
        this.parent = parent;
        this.type = type;
    }

    public SubFolderResponse(String folderId, String name, List<String> parent, String type, Date creationTime, String webViewLink, String webContentLink) {
        this.folderId = folderId;
        this.name = name;
        this.parent = parent;
        this.type = type;
        this.creationTime = creationTime;
        this.webViewLink = webViewLink;
        this.webContentLink = webContentLink;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
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

    public List<SubFolderResponse> getFolderResponseMap() {
        return folderResponseMap;
    }

    public void setFolderResponseMap(List<SubFolderResponse> folderResponseMap) {
        this.folderResponseMap = folderResponseMap;
    }

    public List<FileResponse> getFileResponseMap() {
        return fileResponseMap;
    }

    public void setFileResponseMap(List<FileResponse> fileResponseMap) {
        this.fileResponseMap = fileResponseMap;
    }
}
