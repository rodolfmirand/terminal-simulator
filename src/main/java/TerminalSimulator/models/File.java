package TerminalSimulator.models;

import java.util.Date;

public class File {

    private Directory parent;
    private String name;
    private String data;
    private String owner;
    private Date creatinDate;
    private String group;
    private int bytesSize;
    private String permissions;

    public File(Directory parent, String name, String owner){
        this.parent = parent;
        this.name = name;
        this.owner = owner;
        this.creatinDate = new Date();
        this.permissions = "rw-rw----";
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
    public String toStringLs(){
        return this.permissions + " " + this.owner + " " + this.group + " " + this.bytesSize +  " " + this.name;
    }
}