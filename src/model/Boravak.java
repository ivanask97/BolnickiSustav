package model;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Boravak extends Table {
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;
    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String datumUlaska;
    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String datumIzlaska;
    @Entity(type = "TEXT", size = 500, primary = false, isnull = false)
    String simptom;
    @Entity(type = "TEXT", size = 500, primary = false, isnull = false)
    String dijagnoza;
    @Entity(type = "TEXT", size = 500, primary = false, isnull = false)
    String lijekovi;
    @ForeignKey(table = "Pacijent", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int pacijentFK;

    public int getId() {
        return id;
    }

    public String getDatumUlaska() {
        return datumUlaska;
    }

    public void setDatumUlaska(String datumUlaska) {
        this.datumUlaska = datumUlaska;
    }

    public String getDatumIzlaska() {
        return datumIzlaska;
    }

    public void setDatumIzlaska(String datumIzlaska) {
        this.datumIzlaska = datumIzlaska;
    }

    public String getSimptom() {
        return simptom;
    }

    public void setSimptom(String simptom) {
        this.simptom = simptom;
    }

    public String getDijagnoza() {
        return dijagnoza;
    }

    public void setDijagnoza(String dijagnoza) {
        this.dijagnoza = dijagnoza;
    }

    public String getLijekovi() {
        return lijekovi;
    }

    public void setLijekovi(String lijekovi) {
        this.lijekovi = lijekovi;
    }

    public int getPacijentFK() {
        return pacijentFK;
    }

    public void setPacijentFK(int pacijentFK) {
        this.pacijentFK = pacijentFK;
    }
//ispis svih boravaka
    public static List<?> boravci(Class cls) throws Exception {
        String SQL = "SELECT * FROM boravak ";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> boravci = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            boravci.add(obj);
        }
        return boravci;
    }
    //ispis svih boravaka sa stranim ključem pacijenta
    public static List<Boravak> boravciPac(int id) throws Exception{
        List<Boravak>boravcii=new ArrayList<>();
        String sql="SELECT * FROM boravak WHERE pacijentFK=? ";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        while(rs.next()){
            boravcii.add((Boravak) Boravak.get(Boravak.class,rs.getInt(1)));
        }
        return boravcii;
    }

}
