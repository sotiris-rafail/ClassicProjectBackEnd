package com.classic.project.model.constantParty.file;

public enum FileType {

    IMAGE("image/"),
    FOLDER("application/vnd.google-apps.folder"),
    ROOT("root");

    private String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static FileType getType(String type) {
        FileType[] types = FileType.values();
        for (FileType fileType : types) {
            if(type.contains(fileType.getType())){
                return fileType;
            }
        }
        return null;
    }
}
