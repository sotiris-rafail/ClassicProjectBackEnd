package com.classic.project.model.constantParty.response.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootFolderResponse {

    private String folderId;
    private String name;
    private List<String> parent;
    private String type;

    private Map<String, SubFolderResponse> folderResponseMap = new HashMap<>();

    public RootFolderResponse(String folderId, String name,  List<String> parents, String type) {
        this.folderId = folderId;
        this.name = name;
        this.parent = parents;
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

    public  List<String> getParent() {
        return parent;
    }

    public void setParent( List<String> parent) {
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
}
