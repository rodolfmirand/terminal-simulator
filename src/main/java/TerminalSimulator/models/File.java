package TerminalSimulator.models;

public class File {

    private String path;
    private String name;
    private String data;
    private String owner;

    public File(String path, String name, String data, String owner){
        this.path = path + "/" + name;
        this.name = name;
        this.data = data;
        this.owner = owner;
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
}
