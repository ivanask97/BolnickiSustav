package model;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Lijek extends Table {
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;
    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String nazivLijeka;
    @Entity(type= "INTEGER", size=50, primary=false, isnull=false)
    int količina;
    @Entity(type= "FLOAT", size=50, primary=false, isnull=false)
    float cijena;
    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String proizvodjac;

    public float getCijena() {
        return cijena;
    }

    public void setCijena(float cijena) {
        this.cijena = cijena;
    }

    public String getNazivLijeka() {
        return nazivLijeka;
    }

    public void setNazivLijeka(String nazivLijeka) {
        this.nazivLijeka = nazivLijeka;
    }

    public int getKoličina() {
        return količina;
    }

    public void setKoličina(int količina) {
        this.količina = količina;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }
    //ispis lijekova
    public static List<?> lijek(Class cls) throws Exception {
        String SQL = "SELECT * FROM lijek ";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> lijek = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            lijek.add(obj);
        }
        return lijek;
    }
    public static boolean postLijek(String userName, String imena) throws Exception{
        String sql="SELECT * FROM lijek WHERE nazivLijeka=? AND proizvodjac=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setString(1,userName);
        query.setString(2,imena);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
}
