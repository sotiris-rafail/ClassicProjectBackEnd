package com.classic.project.model.constantParty.response.file;

import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.constantParty.file.FileType;
import com.classic.project.model.constantParty.file.parentFile.ParentFile;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNewFile {

    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private Date creationTime;
    private List<String> parent;

    public AddNewFile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getParent() {
        return parent;
    }

    public void setParent(List<String> parent) {
        this.parent = parent;
    }

    public static CpFile convertNewFileToCPFile(AddNewFile cpFile) {
        List<ParentFile> parentFiles = new ArrayList<>();
        parentFiles.add(new ParentFile(cpFile.getParent().get(0)));
        return new CpFile(cpFile.getName(), FileType.valueOf(cpFile.getType()), cpFile.getCreationTime(), parentFiles);
    }
}
