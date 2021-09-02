package model;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Odjel extends Table {

    @Entity(type= "INTEGER", size=32, primary=true)
    int id;
    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String naziv;
    @Entity(type= "INTEGER", size=50, primary=false, isnull=false)
    int brKreveta;

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrKreveta() {
        return brKreveta;
    }

    public void setBrKreveta(int brKreveta) {
        this.brKreveta = brKreveta;
    }

    public static List<?> odjel(Class cls) throws Exception {
        String SQL = "SELECT * FROM odjel ";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> odjel = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            odjel.add(obj);
        }
        return odjel;
    }
    public static boolean postojiod(String naziv) throws Exception{
        String sql="SELECT * FROM odjel WHERE naziv=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setString(1,naziv);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static List<String> odjeli() throws Exception{
        List<String>odjeljenja=new ArrayList<>();
        String sql="SELECT naziv FROM odjel ";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        ResultSet rs=query.executeQuery();
       while(rs.next()){
           odjeljenja.add(rs.getString("naziv"));
       }
       return odjeljenja;
    }
    public static int odfk(String naziv) throws Exception{
        String sql="SELECT * FROM odjel WHERE naziv=? ";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setString(1,naziv);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return rs.getInt("id");
        }else{
            return 0;
        }
    }
    public static int krevet(int naziv) throws Exception{
        String sql="SELECT * FROM odjel WHERE id=? ";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,naziv);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return rs.getInt("brKreveta");
        }else{
            return 0;
        }
    }
}
