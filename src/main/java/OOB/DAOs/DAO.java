//AUTHOR EOIN HAMILL

package OOB.DAOs;
import OOB.DTOs.Game_Information;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    private String url = "jdbc:mysql://localhost/";
    private String dbname = "videogames";
    private String username = "root";
    private String password = "";

    private static DAO instance;

    private DAO() {

    }

    public static DAO getInstance() {
        if (instance == null)
            instance = new DAO();

        return instance;
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection
                    (url + dbname, username, password);
            return conn;
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
            return null;
        }
    }

    //METHODS FUNCTION 1
    public Game_Information getGame_InformationID(int id) throws SQLException{
        Connection connection = getConnection();
        Game_Information game =null;
        return game;
    }




}

