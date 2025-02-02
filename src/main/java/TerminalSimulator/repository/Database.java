package TerminalSimulator.repository;

import TerminalSimulator.models.Directory;
import TerminalSimulator.models.File;

import java.util.ArrayList;

public class Database {

    private Directory root;

    private String currentUser;


    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public Database() {
        this.root = new Directory("root", null);
    }

    public Directory getRoot() {
        return root;
    }

    public void createDirectory(ArrayList<String> path, String name) {
        Directory parent = findDirectory(root, path);
        if (parent != null) {
            parent.addDirectory(new Directory(name, parent));
        } else {
            System.out.println("Directory not found!");
        }
    }

    public void removeDirectory(String name) {
        if (root.getName().equals(name)) {
            System.out.println("Impossible to remove root directory!");
            return;
        }
        removeDirectory(root, name);
    }

    private void removeDirectory(Directory parent, String name) {
        for (Directory childDir : parent.getDirectories()) {
            if (childDir.getName().equals(name)) {
                parent.removeDirectory(name);
            }
        }
    }

    public void removeFile(ArrayList<String> path, String name) {
        Directory parent = findDirectory(root, path);
        for (File file : parent.getFiles()) {
            if (file.getName().equals(name)) {
                parent.removeFile(name);
            }
        }
    }

    public Directory findDirectory(ArrayList<String> path) {
        return findDirectory(root, path);
    }

    private Directory findDirectory(Directory current, ArrayList<String> path) {
        if (path.size() == 1) {
            return current;
        }
        for (Directory child : current.getDirectories()) {
            if (child.getName().equals(path.get(1))) {
                path.removeFirst();
                return findDirectory(child, path);
            }
        }
        return null;
    }
}
