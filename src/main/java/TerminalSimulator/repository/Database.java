package TerminalSimulator.repository;

import TerminalSimulator.models.Directory;
import TerminalSimulator.models.File;

public class Database {

        private Directory root;

        public Database () {
            this.root = new Directory("", "root");
        }

        public Directory getRoot(){
            return root;
        }

        public void createDirectory(String parentName, String name){
            Directory parent = findDirectory(root, parentName);
            if(parent != null){
                parent.addDirectory(new Directory(parent.getPath(), name));
            }else{
                System.out.println("Directory not found!");
            }
        }

        public void createFile(String parentName, String name, String data) {
            Directory parent = findDirectory(root, parentName);
            if(parent != null){
                parent.addFile(new File(parent.getPath(), name, data));
            }
        }

        public void removeDirectory(String name){
            if(root.getName().equals(name)){
                System.out.println("Impossible to remove root directory!");
                return;
            }
            removeDirectory(root, name);
        }

        private void removeDirectory(Directory parent, String name){
            for(Directory childDir : parent.getDirectories()){
                if(childDir.getName().equals(name)){
                    parent.removeDirectory(name);
                }
            }
        }

        public void removeFile(String parentName, String name){
            Directory parent = findDirectory(root, parentName);
            for(File file : parent.getFiles()){
                if(file.getName().equals(name)){
                    parent.removeFile(name);
                }
            }
        }

        public Directory findDirectory(Directory current, String name){
            if(current.getName().equals(name))
                return current;

            for(Directory childDir : current.getDirectories()){
                Directory found = findDirectory(childDir, name);
                if(found != null) return found;
            }
            return null;
        }
}
