package com.classic.project.model.constantParty.response.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubFolderResponse {

    private String folderId;
    private String name;
    private List<String> parent;
    private String type;

    private Map<String, SubFolderResponse> folderResponseMap = new HashMap<>();
    private Map<String, FileResponse> fileResponseMap = new HashMap<>();

    public SubFolderResponse(String folderId, String name, List<String> parent, String type) {
        this.folderId = folderId;
        this.name = name;
        this.parent = parent;
        this.type = type;
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

    public Map<String, SubFolderResponse> getFolderResponseMap() {
        return folderResponseMap;
    }

    public void setFolderResponseMap(Map<String, SubFolderResponse> folderResponseMap) {
        this.folderResponseMap = folderResponseMap;
    }

    public Map<String, FileResponse> getFileResponseMap() {
        return fileResponseMap;
    }

    public void setFileResponseMap(Map<String, FileResponse> fileResponseMap) {
        this.fileResponseMap = fileResponseMap;
    }
}
