//AUTHOR EOIN HAMILL

package OOB.DAOs;
import OOB.DTOs.Game_Information;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class DAO {

    private static DAO instance;

    private DAO() {

    }

    // if a DAO instance does not exist it will create one
    public static DAO getInstance() {
        if (instance == null)
            instance = new DAO();

        return instance;
    }

    //Setting up a connection to the database
    public Connection getConnection() {
        try {
            //Making a connection with the database
            String url = "jdbc:mysql://localhost/";
            String dbname = "videogames";
            String username = "root";
            String password = "";
            Connection conn = DriverManager.getConnection
                    (url + dbname, username, password);
            return conn;
            //Will return this message if it cant connect with the database
        } catch (SQLException e) {
            System.out.println("Unable to connect to database"+e.getMessage());
            return null;
        }
    }

    //METHODS FUNCTION 1
    //Author Eoin Hamill wrote this code
    public List<Game_Information> getAllGames() throws SQLException{
        Connection connection = getConnection();
        List<Game_Information> game =new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet result = state.executeQuery("SELECT * from gameinformation");

        while(result.next())
        {
            Game_Information addingGame = new Game_Information();
            addingGame.setGameId(result.getInt("GameId"));
            addingGame.setGame_name(result.getString("Game_name"));
            addingGame.setGame_console(result.getString("Game_console"));
            addingGame.setGame_developer(result.getString("Game_developer"));
            addingGame.setGame_franchise(result.getString("Game_developer"));
            addingGame.setMultiplayer(result.getBoolean("Multiplayer"));
            addingGame.setPlayer_amount(result.getInt("Player_amount"));
            addingGame.setReview_Score(result.getInt("Review_Score"));
            addingGame.setImage(result.getString("Image_ID"));
            game.add(addingGame);

        }
        connection.close();
        return game;
    }

    //Author Eoin Hamill wrote this code
    public Game_Information getGameById(int id) throws SQLException {
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
            game.setImage(result.getString("Image_ID"));
        }
        connection.close();
        return game;
    }

    //AUTHOR Eoin Hamill wrote this code
    public void deleteGameById(int id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM gameinformation WHERE GAMEId = "+id);
        deleteStatement.executeUpdate();

    }

    //AUTHOR Eoin Hamill wrote this code
    //Liza added in the generated key code
    //method to add a game
    public void insertGame(Game_Information game) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO gameinformation (Game_name, Game_console,Game_publisher,Game_developer,Game_franchise,Multiplayer,Player_amount,Review_Score,Image_ID) Values(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        insertStatement.setString(1, game.getGame_name());
        insertStatement.setString(2, game.getGame_console());
        insertStatement.setString(3, game.getGame_publisher());
        insertStatement.setString(4, game.getGame_developer());
        insertStatement.setString(5, game.getGame_franchise());
        insertStatement.setBoolean(6, game.getMultiplayer());
        insertStatement.setInt(7, game.getPlayer_amount());
        insertStatement.setDouble(8, game.getReview_Score());
        insertStatement.setString(9, game.getImage());

        ResultSet results = insertStatement.getGeneratedKeys();
        if (results.next())
        {
            game.setGameId(results.getInt(1));
        }

        insertStatement.executeUpdate();
    }

    //Author Dovydas and Eoin
    public void updateGameInfo(int id, Game_Information game) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement insertStatement = connection.prepareStatement("""
                UPDATE gameinformation\s
                SET Game_name =?, Game_console=?,Game_developer=?,Game_franchise=?,Game_publisher=?,Multiplayer=?,Player_amount=?,Review_Score=?,Image_ID=?
                WHERE GameID =?""");
        insertStatement.setString(1, game.getGame_name());
        insertStatement.setString(2, game.getGame_console());
        insertStatement.setString(3, game.getGame_publisher());
        insertStatement.setString(4, game.getGame_developer());
        insertStatement.setString(5, game.getGame_franchise());
        insertStatement.setBoolean(6, game.getMultiplayer());
        insertStatement.setInt(7, game.getPlayer_amount());
        insertStatement.setDouble(8, game.getReview_Score());
        insertStatement.setString(9, game.getImage());
        insertStatement.setInt(10,id);


        insertStatement.executeUpdate();
    }



    //AUTHOR EOIN HAMILL wrote this entire function
    public List<Game_Information> gameInformationBasedOnName(Comparator<Game_Information> gameNameComparator) throws SQLException{
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter Name You would like to filter by");
        String filter=kb.nextLine();
        Connection connection = getConnection();
        List<Game_Information> game =new ArrayList<>();
        Statement state = connection.createStatement();
        ResultSet result = state.executeQuery("SELECT * from gameinformation");

        while(result.next())
        {
            Game_Information addingGame = new Game_Information();
            addingGame.setGameId(result.getInt("GameId"));
            addingGame.setGame_name(result.getString("Game_name"));
            addingGame.setGame_console(result.getString("Game_console"));
            addingGame.setGame_developer(result.getString("Game_developer"));
            addingGame.setGame_franchise(result.getString("Game_developer"));
            addingGame.setMultiplayer(result.getBoolean("Multiplayer"));
            addingGame.setPlayer_amount(result.getInt("Player_amount"));
            addingGame.setReview_Score(result.getInt("Review_Score"));
            addingGame.setImage(result.getString("Image_ID"));

            if(gameNameComparator.compare(addingGame, new Game_Information(filter))==0)
            {
                game.add(addingGame);
            }



        }
        connection.close();
        return game;
    }




}