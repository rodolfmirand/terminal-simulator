package TerminalSimulator.repository;

import TerminalSimulator.models.Directory;
import TerminalSimulator.models.File;

import java.util.ArrayList;
import java.util.List;

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

    public String tree() {
        StringBuilder builder = new StringBuilder();
        buildTreeString(root, "", true, builder);
        return builder.toString();
    }

    private void buildTreeString(Directory directory, String prefix, boolean isLast, StringBuilder builder) {
        builder.append(prefix).append(isLast ? "└── " : "├── ").append(directory.getName()).append("\n");

        String newPrefix = prefix + (isLast ? "    " : "│   ");

        List<Directory> subDirs = directory.getDirectories();
        List<File> files = directory.getFiles();

        int totalItems = subDirs.size() + files.size();

        for (int i = 0; i < subDirs.size(); i++) {
            boolean lastItem = (i == totalItems - 1 && files.isEmpty());
            buildTreeString(subDirs.get(i), newPrefix, lastItem, builder);
        }

        for (int i = 0; i < files.size(); i++) {
            boolean lastFile = (i == files.size() - 1);
            builder.append(newPrefix)
                    .append(lastFile ? "└── " : "├── ")
                    .append(files.get(i).getName())
                    .append("\n");
        }
    }
}
