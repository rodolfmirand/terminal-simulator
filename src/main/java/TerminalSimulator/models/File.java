package TerminalSimulator.models;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class File {
    private static final AtomicInteger inodeCounter = new AtomicInteger(1);

    private Directory parent;
    private String name;
    private String data;
    private String owner;
    private Date creationDate;
    private String group;
    private int bytesSize;
    private String permissions;

    private int inode;
    private Date accessTime;
    private Date modifyTime;
    private Date changeTime;

    public File(Directory parent, String name, String owner){
        this.parent = parent;
        this.name = name;
        this.owner = owner;
        this.creationDate = new Date();
        this.permissions = "rw-rw----";
        this.inode = inodeCounter.getAndIncrement();
        this.accessTime = new Date();
        this.modifyTime = new Date();
        this.changeTime = new Date();
    }

    public String getName() {
        updateAccessTime();
        return name;
    }

    public void setName(String name) {
        updateModifyTime();
        updateChangeTime();
        this.name = name;
    }

    public String getData() {
        updateAccessTime();
        return data;
    }

    public void setData(String data) {
        updateModifyTime();
        updateChangeTime();
        this.data = data;
    }

    public void setBytesSize() {
        updateModifyTime();
        updateChangeTime();
        this.bytesSize = this.data.length() * 8;
    }
    public int getSize() {
        updateAccessTime();
        return this.bytesSize;
    }

    public void setPermissions(String permissions) {
        updateModifyTime();
        updateChangeTime();
        this.permissions = permissions;
    }

    public void setOwner(String owner) {
        updateModifyTime();
        updateChangeTime();
        this.owner = owner;
    }
    public String toStringLs(){
        updateAccessTime();
        return this.permissions + " " + this.owner + " " + this.group + " " + this.bytesSize +  " " + this.name;
    }

    public String getFileInfo() {
        return "Inode: " + inode +
                " | Access: " + accessTime +
                " | Modify: " + modifyTime +
                " | Change: " + changeTime +
                " | Size: " + getSize() + " bytes";
    }

    private void updateAccessTime() {
        this.accessTime = new Date();
    }

    private void updateModifyTime() {
        this.modifyTime = new Date();
    }

    private void updateChangeTime() {
        this.changeTime = new Date();
    }
}