//AUTHOR EOIN HAMILL

package OOB.DAOs;
import OOB.DTOs.Game_Information;
import com.google.gson.Gson;
import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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
            System.out.println("Unable to connect to database"+e.getMessage());
            return null;
        }
    }

    //METHODS FUNCTION 1
    //Author Eoin Hamill Created this entire function
    public List<Game_Information> getAllGames() throws SQLException{
        DAO dao =DAO.getInstance();
        Connection connection = getConnection();
        List<Game_Information> game =new ArrayList();
        Statement state = connection.createStatement();
        ResultSet result = state.executeQuery("SELECT * from gameinformation");

        while(result.next())
        {
            Game_Information addinggame = new Game_Information();
            addinggame.setGameId(result.getInt("GameId"));
            addinggame.setGame_name(result.getString("Game_name"));
            addinggame.setGame_console(result.getString("Game_console"));
            addinggame.setGame_developer(result.getString("Game_developer"));
            addinggame.setGame_franchise(result.getString("Game_developer"));
            addinggame.setMultiplayer(result.getBoolean("Multiplayer"));
            addinggame.setPlayer_amount(result.getInt("Player_amount"));
            addinggame.setReview_Score(result.getInt("Review_Score"));
            game.add(addinggame);

        }
        connection.close();
        return game;
    }

    //Author Eoin Hamill created this entire function
    public Game_Information getGameById(int id) throws SQLException {
        DAO dao =DAO.getInstance();
        Connection connection = getConnection();
        Statement state = connection.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM gameinformation WHERE GAMEId = "+id);
        Game_Information game = new Game_Information();
        while(result.next())
        {
            game.setGameId(result.getInt("GameId"));
            game.setGame_name(result.getString("Game_name"));
            game.setGame_console(result.getString("Game_console"));
            game.setGame_developer(result.getString("Game_developer"));
            game.setGame_franchise(result.getString("Game_developer"));
            game.setMultiplayer(result.getBoolean("Multiplayer"));
            game.setPlayer_amount(result.getInt("Player_amount"));
            game.setReview_Score(result.getInt("Review_Score"));
        }
        connection.close();
        return game;
    }

    //AUTHOR Eoin Hamill created this entire function
    public void deleteGameById(int id) throws SQLException {
        DAO dao =DAO.getInstance();
        Connection connection = getConnection();
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM gameinformation WHERE GAMEId = "+id);
        deleteStatement.executeUpdate();

    }

    //AUTHOR Eoin Hamill created this entire function
    public void insertGame(Game_Information game) throws SQLException {
        DAO dao =DAO.getInstance();
        Connection connection = getConnection();

        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO gameinformation (Game_name, Game_console,Game_publisher,Game_developer,Game_franchise,Multiplayer,Player_amount,Review_Score) Values(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        insertStatement.setString(1, game.getGame_name());
        insertStatement.setString(2, game.getGame_console());
        insertStatement.setString(3, game.getGame_publisher());
        insertStatement.setString(4, game.getGame_developer());
        insertStatement.setString(5, game.getGame_franchise());
        insertStatement.setBoolean(6, game.getMultiplayer());
        insertStatement.setInt(7, game.getPlayer_amount());
        insertStatement.setDouble(8, game.getReview_Score());

        insertStatement.executeUpdate();

        ResultSet results = insertStatement.getGeneratedKeys();
        if (results.next())
        {
            game.setGameId(results.getInt(1));
        }
    }

    //Author Dovydas and Eoin
    public void updateGameInfo(int id, Game_Information game) throws SQLException {
        DAO dao =DAO.getInstance();
        Connection connection = getConnection();

        PreparedStatement insertStatement = connection.prepareStatement("UPDATE gameinformation \n" +
                "SET Game_name =?, Game_console=?,Game_developer=?,Game_franchise=?,Game_publisher=?,Multiplayer=?,Player_amount=?,Review_Score=?\n" +
                "WHERE GameID =?");
        insertStatement.setString(1, game.getGame_name());
        insertStatement.setString(2, game.getGame_console());
        insertStatement.setString(3, game.getGame_publisher());
        insertStatement.setString(4, game.getGame_developer());
        insertStatement.setString(5, game.getGame_franchise());
        insertStatement.setBoolean(6, game.getMultiplayer());
        insertStatement.setInt(7, game.getPlayer_amount());
        insertStatement.setDouble(8, game.getReview_Score());
        insertStatement.setInt(9,id);

        insertStatement.executeUpdate();
            }



    //AUTHOR EOIN HAMILL wrote this entire function
    public List<Game_Information> gameInformationBasedOnName(Comparator<Game_Information> gamenameComparator) throws SQLException{
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter Name You would like to filter by");
        String filter=kb.nextLine();
        DAO dao =DAO.getInstance();
        Connection connection = getConnection();
        List<Game_Information> game =new ArrayList();
        Statement state = connection.createStatement();
        ResultSet result = state.executeQuery("SELECT * from gameinformation");

        while(result.next())
        {
            Game_Information addinggame = new Game_Information();
            addinggame.setGameId(result.getInt("GameId"));
            addinggame.setGame_name(result.getString("Game_name"));
            addinggame.setGame_console(result.getString("Game_console"));
            addinggame.setGame_developer(result.getString("Game_developer"));
            addinggame.setGame_franchise(result.getString("Game_developer"));
            addinggame.setMultiplayer(result.getBoolean("Multiplayer"));
            addinggame.setPlayer_amount(result.getInt("Player_amount"));
            addinggame.setReview_Score(result.getInt("Review_Score"));

            if(gamenameComparator.compare(addinggame, new Game_Information(filter))==0)
            {
                game.add(addinggame);
            }



        }
        connection.close();
        return game;
    }




}

