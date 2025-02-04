package TerminalSimulator.models;

import java.util.Date;

public class File {

    private String path;
    private String name;
    private String data;
    private String owner;
    private Date creatinDate;
    private int bytesSize;
    private String permissions;

    public File(String path, String name, String owner){
        this.path = path + "/" + name;
        this.name = name;
        this.owner = owner;
        this.creatinDate = new Date();
        this.permissions = "rw-rw----";
    }

    public File(String path, String name) {
        this.path = path + "/" + name;
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public void setBytesSize() {
    	this.bytesSize = this.data.length() * 8;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setOwner(String owner) { this.owner = owner;}
}
