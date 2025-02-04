package TerminalSimulator.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Directory {

    private String name;
    private List<File> files;
    private List<Directory> directories;
    private Directory parent;
    private Date creationDate;
    private String owner;
    private String permissions;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.files = new ArrayList<>();
        this.directories = new ArrayList<>();
        this.parent = parent;
        this.creationDate = new Date();
        this.permissions = "rw-rw----";
    }

    public void addFile(File file) {
        this.files.add(file);
    }

    public void addDirectory(Directory directory) {
        this.directories.add(directory);
    }

    public void removeDirectory(String name) {
        this.directories.removeIf(childDir -> childDir.getName().equals(name));
    }

    public void removeFile(String name) {
        this.files.removeIf(childDir -> childDir.getName().equals(name));
    }

    public Directory findChildDirectory(String name) {
        for (Directory childDir : this.directories) {
            if (childDir.getName().equals(name)) {
                return childDir;
            }
        }
        return null;
    }

    public File findFile(String name) {
        for (File file : this.files) {
            if (file.getName().equals(name)) {
                return file;
            }
        }

        return null;
    }

    public String getPath() {
        String path = this.name;
        Directory parent = this.parent;
        while (true) {
            if (parent == null) {
                break;
            }
            path = parent.getName() + "/" + path;
            parent = parent.getParent();
        }
        return path;
    }

    public Directory findSubDirectory(String name) {
        for (Directory directory : this.directories) {
            if (directory.getName().equals(name)) {
                return directory;
            }
        }
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getFiles() {
        return files;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }
    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setOwner(String owner) { this.owner = owner; }
}
