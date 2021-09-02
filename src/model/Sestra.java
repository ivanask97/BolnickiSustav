package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Sestra extends Table {
    @Entity(type= "INTEGER", size=32, primary=true)
    int id;

    @ForeignKey(table = "User", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int userFK;

    @ForeignKey(table = "Odjel", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int odjelFK;

    public int getId() {
        return id;
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

    public static Sestra ussr(int id) throws Exception{
        String sql="SELECT * FROM sestra WHERE userFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (Sestra) Sestra.get(Sestra.class,rs.getInt(1));
        }else{
            return null;
        }
    }

    public static boolean uspost(int id) throws Exception{
        String sql="SELECT * FROM sestra WHERE userFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }

    public static Sestra odsr(int id) throws Exception{
        String sql="SELECT * FROM sestra WHERE odjelFK=?";
        PreparedStatement query=Database.CONNECTION.prepareStatement(sql);
        query.setInt(1,id);
        ResultSet rs=query.executeQuery();
        if(rs.next()){
            return (Sestra) Sestra.get(Sestra.class,rs.getInt(1));
        }else{
            return null;
        }
    }
}
