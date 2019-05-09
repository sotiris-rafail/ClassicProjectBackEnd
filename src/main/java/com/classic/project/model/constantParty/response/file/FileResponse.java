package com.classic.project.model.constantParty.response.file;

import java.util.List;

public class FileResponse {

    private String fileId;
    private String name;
    private List<String> parent;
    private String type;

    public FileResponse(String fileId, String name, List<String> parent, String type) {
        this.fileId = fileId;
        this.name = name;
        this.parent = parent;
        this.type = type;
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
}
