package com.classic.project.model.constantParty.file;

import java.io.Serializable;
import java.util.Objects;

public class ParentFileId  implements Serializable {

    private String fileId;
    private String parentId;

    public ParentFileId(String fileId, String parentId) {
        this.parentId = parentId;
        this.fileId = fileId;
    }

    public ParentFileId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentFileId that = (ParentFileId) o;
        return fileId == that.fileId &&
                fileId.equals(that.fileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, parentId);
    }
}
