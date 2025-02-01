package TerminalSimulator.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Directory {

    private String path;
    private String name;
    private List<File> files;
    private List<Directory> directories;
    private Directory parent;
    private Date creationDate;
    private String owner;

    public Directory(String path, String name, Directory parent) {
        this.path = path;
        this.name = name;
        this.files = new ArrayList<>();
        this.directories = new ArrayList<>();
        this.parent = parent;
        this.creationDate = new Date();
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
}
