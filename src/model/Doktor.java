package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Doktor extends Table {
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;

    @Entity(type= "VARCHAR", size=150, primary=false, isnull=false)
    String specijalizacija;

    @ForeignKey(table = "User", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int userFK;

    @ForeignKey(table = "Odjel", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int odjelFK;

    public int getId() {
        return id;
    }

    public String getSpecijalizacija() {
        return specijalizacija;
    }

    public void setSpecijalizacija(String specijalizacija) {
        this.specijalizacija = specijalizacija;
    }

    public int getUserFK() {
        return userFK;
    }

    public void setUserFK(int userFK) {
        this.userFK = userFK;
    }

    public int getOdjelFK() {
        return odjelFK;
    }

    public void setOdjelFK(int odjelFK) {
        this.odjelFK = odjelFK;
    }
//ispis doktora koji ima određeni strani ključ korisnika
    public static Doktor usdr(int id) throws Exception{
        String sql="SELECT * FROM doktor WHERE userFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (Doktor) Doktor.get(Doktor.class,rs.getInt(1));
        }else{
            return null;
        }
    }
    //ispis doktora sa određenim id-om
    public static Doktor drid(int id) throws Exception{
        String sql="SELECT * FROM doktor WHERE id=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (Doktor) Doktor.get(Doktor.class,rs.getInt(1));
        }else{
            return null;
        }
    }
    //vrati doktora koji ima traženi ključ odjela
    public static Doktor oddr(int id) throws Exception{
        String sql="SELECT * FROM doktor WHERE odjelFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (Doktor) Doktor.get(Doktor.class,rs.getInt(1));
        }else{
            return null;
        }
    }
    public static boolean uspos(int id) throws Exception{
        String sql="SELECT * FROM doktor WHERE userFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }
    //lista svih doktora sa određenim stranim ključem odjela
    public static List<Doktor> sviDokt(int id) throws Exception{
        List<Doktor> doktPac=new ArrayList<>();
        String sql="SELECT * FROM doktor WHERE odjelFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        while(rs.next()){
            doktPac.add((Doktor) Doktor.get(Doktor.class,rs.getInt(1)));
        }return doktPac;
    }

}
