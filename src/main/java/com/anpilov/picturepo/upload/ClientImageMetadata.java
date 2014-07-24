package com.anpilov.picturepo.upload;

public class ClientImageMetadata {

    private String id;
    private String name;
    private String path;

    private String similarityGroup;
    private boolean ignored;
    private boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSimilarityGroup() {
        return similarityGroup;
    }

    public void setSimilarityGroup(String similarityGroup) {
        this.similarityGroup = similarityGroup;
    }

    public boolean isIgnored() {
        return ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
