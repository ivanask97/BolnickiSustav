package model;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pregled extends Table {
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;
    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String datumPregleda;
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

    public String getDatumPregleda() {
        return datumPregleda;
    }

    public void setDatumPregleda(String datumPregleda) {
        this.datumPregleda = datumPregleda;
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

    public static List<?> pregledi(Class cls) throws Exception {
        String SQL = "SELECT * FROM pregled ";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> pregledi = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            pregledi.add(obj);
        }
        return pregledi;
    }
    public static List<Pregled> preglediPac(int id) throws Exception{
        List<Pregled>pregledi=new ArrayList<>();
        String sql="SELECT * FROM pregled WHERE pacijentFK=? ";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        while(rs.next()){
            pregledi.add((Pregled) Pregled.get(Pregled.class,rs.getInt(1)));
        }
        return pregledi;
    }

}
