package model;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Operacija extends Table {
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;
    @Entity(type= "VARCHAR", size=50, primary=false, isnull=false)
    String nazivSale;

    public int getId() {
        return id;
    }

    public String getNazivSale() {
        return nazivSale;
    }

    public void setNazivSale(String nazivSale) {
        this.nazivSale = nazivSale;
    }
    public static List<?> sale(Class cls) throws Exception {
        String SQL = "SELECT * FROM operacija ";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> sale = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            sale.add(obj);
        }
        return sale;
    }
    public static boolean postojiop(String nazivSale) throws Exception{
        String sql="SELECT * FROM operacija WHERE nazivSale=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setString(1,nazivSale);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    public static List<String> opsale() throws Exception{
        List<String>sale=new ArrayList<>();
        String sql="SELECT nazivSale FROM operacija ";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        ResultSet rs=query.executeQuery();
        while(rs.next()){
            sale.add(rs.getString("nazivSale"));
        }
        return sale;
    }
    public static int opId(String nazivSale) throws Exception{
        String sql="SELECT * FROM operacija WHERE nazivSale=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setString(1,nazivSale);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return rs.getInt("id");
        }else{
            return 0;
        }
    }
}
