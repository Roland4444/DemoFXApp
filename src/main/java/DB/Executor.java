package DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.sql.DriverManager.getConnection;
public class Executor {
    String db; String login; String passwd;
    private Connection conn;
    public Executor(String db, String login, String passwd) throws SQLException {
        this.db=db;
        this.passwd = passwd;
        this.login=login;
        conn = getConnection(db, login, passwd);
    }
    Statement stmt=null;
    ResultSet res=null;

    public ResultSet submit(String query) throws SQLException{
        stmt = conn.createStatement();
        res = stmt.executeQuery(query);
        return res;
    }

    public String insertBuilder(String Destination, ArrayList data){
        int i=0;
        String query="INSERT INTO "+ Destination +" VALUES(";
        PreparedStatement pst = null;
        while (i++<data.size()){
            if (i==data.size()) {query+="?"; break;};
            query+="?,";
        }
        query+=");";
        return query;
    }

    public String convertDate2String(Date indate)
    {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
         try{
            dateString = sdfr.format( indate );
        }catch (Exception ex ){
            System.out.println(ex);
        }
        return dateString;
    }


    public ResultSet materialinfo2Reports( Date startD, Date stopD, int start, int  stop  ) throws SQLException {
        stmt = conn.createStatement();
        res = stmt.executeQuery("SELECT * FROM lab.materials WHERE ncode IN(SELECT ncode FROM lab.requests WHERE date_ > '"
                +convertDate2String(startD)+"' AND date_ <'"+convertDate2String(stopD)+"' AND nr>"+String.valueOf(start)+ " AND nr<"
                +String.valueOf(stop)+") ORDER BY price DESC, type DESC;");
        return res;
    }

    public ResultSet requestinfo2Reports(Date startD, Date stopD, int start, int  stop ) throws SQLException {
        stmt = conn.createStatement();
        res = stmt.executeQuery("SELECT * FROM lab.requests WHERE ncode IN(SELECT ncode FROM lab.requests WHERE date_ > '"
                +convertDate2String(startD)+"' AND date_ <'"+convertDate2String(stopD)+"' AND nr>"+String.valueOf(start)+ " AND nr<"
                +String.valueOf(stop)+") ;");
        return res;
    }

    public ResultSet reservesinfo2Reports(Date startD, Date stopD, int start, int  stop ) throws SQLException {
        stmt = conn.createStatement();
        res = stmt.executeQuery("SELECT * FROM lab.reserves WHERE ncode IN(SELECT ncode FROM lab.requests WHERE date_ > '"
                +convertDate2String(startD)+"' AND date_ <'"+convertDate2String(stopD)+"' AND nr>"+String.valueOf(start)+ " AND nr<"
                +String.valueOf(stop)+");");
        return res;
    }



    public void insert(String Destination, ArrayList<Object>  data) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(insertBuilder(Destination,data));
        int i=1;

        for (Object o : data) {
            if (o instanceof Integer) {
                pst.setInt(i++, (int)o );
            }
            if (o instanceof String) {
                pst.setString(i++, (String)o );
            }
            if (o instanceof Double) {
                pst.setDouble(i++, (Double)o );
            }

        }
        pst.executeUpdate();
    }

    public void insert(String Destination, String  data) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("INSERT INTO "+ Destination+ "VALUE('"+data+"');");

        pst.executeUpdate();
    }


}

