//AUTHOR EOIN HAMILL

package OOB.DAOs;
import OOB.DTOs.Game_Information;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private String url = "jdbc:mysql://localhost:3306/";
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
            System.out.println("Unable to connect to database"+e.getMessage());
            return null;
        }
    }

    //METHODS FUNCTION 1
    public List<Game_Information> getAllGames() throws SQLException{
        DAO dao =DAO.getInstance();
        Connection connection = getConnection();
        List<Game_Information> game =new ArrayList();
        Statement state = connection.createStatement();
        ResultSet result = state.executeQuery("SELECT * from gameinformation");

        while(result.next())
        {
            Game_Information addinggame = new Game_Information();
            addinggame.setGameId(result.getInt("Game_ID"));
            addinggame.setGame_name(result.getString("Game_name"));
            addinggame.setGame_console(result.getString("Game_console"));
            addinggame.setGame_developer(result.getString("Game_developer"));
            addinggame.setGame_franchise(result.getString("Game_developer"));
            addinggame.setGame_releasedate(result.getString("releasedate"));
            addinggame.setMultiplayer(result.getBoolean("Multiplayer"));
            addinggame.setPlayer_amount(result.getInt("Player_amount"));
            addinggame.setReview_Score(result.getInt("Review_Score"));

            game.add(addinggame);



        }
        connection.close();
        return game;
    }




}

