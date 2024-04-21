//AUTHOR EOIN HAMILL
package OOB.DAOs;
import OOB.DTOs.Game_Information;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class DAO {
    //Setting up variables so you can connect to database
    private String url = "jdbc:mysql://localhost/";
    private String dbname = "videogames";
    private String username = "root";
    private String password = "";

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
    //METHOD TO GET ALL GAMES
    public List<Game_Information> getAllGames() throws SQLException{
        //Setting up connection
        Connection connection = getConnection();
        //Setting up a arraylist of games
        List<Game_Information> game =new ArrayList();
        //Creating a statement for SQL
        Statement state = connection.createStatement();
        //results will = what the query returns. The query gets all from the game information database
        ResultSet result = state.executeQuery("SELECT * from gameinformation");

        //This whiel loop will run through the result and assign them to a game the game will then be added to the arraylist
        while(result.next())
        {
            //Making a new game_information object
            Game_Information addinggame = new Game_Information();
            addinggame.setGameId(result.getInt("GameId"));
            addinggame.setGame_name(result.getString("Game_name"));
            addinggame.setGame_console(result.getString("Game_console"));
            addinggame.setGame_developer(result.getString("Game_developer"));
            addinggame.setGame_franchise(result.getString("Game_developer"));
            addinggame.setMultiplayer(result.getBoolean("Multiplayer"));
            addinggame.setPlayer_amount(result.getInt("Player_amount"));
            addinggame.setReview_Score(result.getInt("Review_Score"));
            addinggame.setImage(result.getString("Image_ID"));
            game.add(addinggame);

        }
        //Closing connection
        connection.close();
        return game;
    }

    //Author Eoin Hamill wrote this code
    //This method gets a game based on ID it takes in a ID.
    public Game_Information getGameById(int id) throws SQLException {
        //Setting up dao and connection
        Connection connection = getConnection();
        //Creating a statement object which I will use to execute database query.
        Statement state = connection.createStatement();
        //results will = what the query returns. The query gets all from the game information database
        //The query is gonna return all where the ID matches which is what the user will put in
        ResultSet result = state.executeQuery("SELECT * FROM gameinformation WHERE GAMEId = "+id);
        //Making a new game_information object and assigning results to it
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
        //closing connection
        connection.close();
        //returning the game which contains results from the query
        return game;
    }

    //AUTHOR Eoin Hamill wrote this code
    public void deleteGameById(int id) throws SQLException {
        //Setting up DAO instance and connecting to database
        Connection connection = getConnection();
        //Making a statement which will delete based on ID taken in
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM gameinformation WHERE GAMEId = "+id);
        //Running the statement
        deleteStatement.executeUpdate();

    }

    //AUTHOR Eoin Hamill wrote this code
    //Liza added in the generated key code
    //method to add a game
    public void insertGame(Game_Information game) throws SQLException {
        Connection connection = getConnection();
        //I write the statement with ? for parameters which I then replace with the information taken in from game.
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO gameinformation (Game_name, Game_console,Game_publisher,Game_developer,Game_franchise,Multiplayer,Player_amount,Review_Score,Image_ID) Values(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        //Replacing the parameters
        insertStatement.setString(1, game.getGame_name());
        insertStatement.setString(2, game.getGame_console());
        insertStatement.setString(3, game.getGame_publisher());
        insertStatement.setString(4, game.getGame_developer());
        insertStatement.setString(5, game.getGame_franchise());
        insertStatement.setBoolean(6, game.getMultiplayer());
        insertStatement.setInt(7, game.getPlayer_amount());
        insertStatement.setDouble(8, game.getReview_Score());
        insertStatement.setString(9, game.getImage());

        //Returning the ID of the created game.
        ResultSet results = insertStatement.getGeneratedKeys();
        if (results.next())
        {
            game.setGameId(results.getInt(1));
        }

        //Running statement
        insertStatement.executeUpdate();
    }

    //Author Dovydas and Eoin
    //This method updates game information.
    //Takes in ID for game you want to update and new information which will be used to update.
    public void updateGameInfo(int id, Game_Information game) throws SQLException {
        DAO dao =DAO.getInstance();
        Connection connection = getConnection();
        //Creating statement with ? parameters which I then replace with the information taken in from game object. I use the Where gameID= to update the particular game the user requested.
        PreparedStatement insertStatement = connection.prepareStatement("UPDATE gameinformation \n" +
                "SET Game_name =?, Game_console=?,Game_developer=?,Game_franchise=?,Game_publisher=?,Multiplayer=?,Player_amount=?,Review_Score=?,Image_ID=?\n" +
                "WHERE GameID =?");
        //Adding in the data to the update statement
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

        //Running statement
        insertStatement.executeUpdate();
    }



    //AUTHOR EOIN HAMILL wrote this entire function
    //This function compares game names to see if they match if they do it returns them in a list.
    public List<Game_Information> gameInformationBasedOnName(Comparator<Game_Information> gamenameComparator) throws SQLException{
        //Creating keyboard
        Scanner kb = new Scanner(System.in);
        //Taking in input
        System.out.println("Enter Name You would like to filter by");
        String filter=kb.nextLine();
        //connection to database
        DAO dao =DAO.getInstance();
        Connection connection = getConnection();
        //Making a new list of gameInformation which will store the data.
        List<Game_Information> game =new ArrayList();
        //Making a new statement
        Statement state = connection.createStatement();
        //Running a query
        ResultSet result = state.executeQuery("SELECT * from gameinformation");
        //While result still has information in it. It will loop this while loop.
        while(result.next())
        {
            //Creating a new game which will store the data taken in.
            Game_Information addinggame = new Game_Information();
            addinggame.setGameId(result.getInt("GameId"));
            addinggame.setGame_name(result.getString("Game_name"));
            addinggame.setGame_console(result.getString("Game_console"));
            addinggame.setGame_developer(result.getString("Game_developer"));
            addinggame.setGame_franchise(result.getString("Game_developer"));
            addinggame.setMultiplayer(result.getBoolean("Multiplayer"));
            addinggame.setPlayer_amount(result.getInt("Player_amount"));
            addinggame.setReview_Score(result.getInt("Review_Score"));
            addinggame.setImage(result.getString("Image_ID"));
            //If the game names match with the user input (so the comparater returns a 0)
            //The game will be added to the list
            if(gamenameComparator.compare(addinggame, new Game_Information(filter))==0)
            {
                game.add(addinggame);
            }



        }
        //Closing connection
        connection.close();
        return game;
    }




}