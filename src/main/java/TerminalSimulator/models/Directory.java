package TerminalSimulator.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Directory {
    private static final AtomicInteger inodeCounter = new AtomicInteger(1);

    private String name;
    private List<File> files;
    private List<Directory> directories;
    private Directory parent;
    private Date creationDate;
    private String owner;
    private String permissions;
    private String group;
    private int bytesSize;
    private int inode;
    private Date accessDate;
    private Date modifyDate;
    private Date changeDate;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.files = new ArrayList<>();
        this.directories = new ArrayList<>();
        this.parent = parent;
        this.creationDate = new Date();
        this.permissions = "drw-rw----";
        this.inode = inodeCounter.getAndIncrement();
        this.accessDate = new Date();
        this.modifyDate = new Date();
        this.changeDate = new Date();
    }

    public void addFile(File file) {
        this.files.add(file);
        updateModifyAndChange();
    }

    public void addDirectory(Directory directory) {
        this.directories.add(directory);
        updateModifyAndChange();
    }

    public boolean removeDirectory(String name) {
        boolean removed = directories.removeIf(childDir -> childDir.getName().equals(name));
        if (removed) {
            updateModifyAndChange();
        }
        return removed;
    }

    public boolean removeFile(String name) {
        boolean removed = files.removeIf(file -> file.getName().equals(name));
        if (removed) {
            updateModifyAndChange();
        }
        return removed;
    }

    public Directory findChildDirectory(String name) {
        for (Directory childDir : directories) {
            if (childDir.getName().equals(name)) {
                updateAccess();
                return childDir;
            }
        }
        return null;
    }

    public File findFile(String name) {
        for (File file : files) {
            if (file.getName().equals(name)) {
                updateAccess();
                return file;
            }
        }
        return null;
    }

    public String getPath() {
        String path = this.name;
        Directory parent = this.parent;
        while (parent != null) {
            path = parent.getName() + "/" + path;
            parent = parent.getParent();
        }
        return path;
    }

    public int calculateSize() {
        int totalSize = 0;
        for (File file : files) {
            totalSize += file.getSize();
        }
        for (Directory directory : directories) {
            totalSize += directory.calculateSize();
        }
        this.bytesSize = totalSize;
        return totalSize;
    }

    private void updateAccess() {
        this.accessDate = new Date();
    }

    private void updateModifyAndChange() {
        this.modifyDate = new Date();
        this.changeDate = new Date();
    }

    public String getDirectoryInfo() {
        return "Inode: " + inode +
                " | Access: " + accessDate +
                " | Modify: " + modifyDate +
                " | Change: " + changeDate +
                " | Size: " + calculateSize() + " bytes";
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<File> getFiles() { return files; }
    public List<Directory> getDirectories() { return directories; }
    public Directory getParent() { return parent; }
    public void setParent(Directory parent) { this.parent = parent; }
    public void setPermissions(String permissions) { this.permissions = permissions; }
    public void setOwner(String owner) { this.owner = owner; }

    public String toStringLs() {
        return this.permissions + " " + this.owner + " " + this.group + " " + this.bytesSize + " " + this.name;
    }

    public Directory findSubDirectory(String name) {
        for (Directory directory : this.directories) {
            if (directory.getName().equals(name)) {
                return directory;
            }
        }
        return null;
    }
}