package model;

public class BoravakPac extends Table {
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;
    @ForeignKey(table = "Pacijent", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int pacijentFK;
    @ForeignKey(table = "Pacijent", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int boravakFK;

    public int getId() {
        return id;
    }

    public int getPacijentFK() {
        return pacijentFK;
    }

    public void setPacijentFK(int pacijentFK) {
        this.pacijentFK = pacijentFK;
    }

    public int getBoravakFK() {
        return boravakFK;
    }

    public void setBoravakFK(int boravakFK) {
        this.boravakFK = boravakFK;
    }
}
