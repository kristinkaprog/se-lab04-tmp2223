package server;

public class Database {
    String nameOfFile;
    int id;

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Database(String nameOfFile, int id) {
        this.nameOfFile = nameOfFile;
        this.id = id;
    }
}


