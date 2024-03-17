//AUTHOR EOIN HAMILL

package OOB.DAOs;
import OOB.DTOs.Game_Information;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
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
            addinggame.setGame_releasedate(result.getString("Game_releasedate"));
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
            game.setGame_releasedate(result.getString("Game_releasedate"));
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

        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO gameinformation VALUES(Game_name, Game_console,Game_publisher,Game_developer,Game_franchise,Game_releasedate,Multiplayer,Player_amount,Review_Score) (\""+game.getGame_name()+"\",\""+game.getGame_console()+"\",\""+game.getGame_developer()+"\",\""+game.getGame_publisher()+"\",\""+game.getGame_franchise()+"\",\""+game.getGame_releasedate()+"\","+game.getMultiplayer()+","+game.getPlayer_amount()+","+game.getReview_Score()+")");
        insertStatement.executeUpdate();
    }

    //AUTHOR DOVYDAS created this function
    public static void updateGameInfo(int id, Game_Information game) {
        Scanner kb = new Scanner(System.in);
        String userChange;      boolean userBoolChange;     int userPlayerChange;

        if (id==1)
        {
            System.out.println("Which variable would you like to Update");
            System.out.print("1. Game Console\n2. Multiplayer\n3. Player Amount \n4. Review Score\n");
            int choice = kb.nextInt();
            if (choice ==1) {
                System.out.println(game.getGame_name()+" :What Console can I play at");
                userChange = kb.nextLine();

            }
            else if (choice == 2){
                System.out.println(game.getGame_name()+" :Did it change the Multiplayer (True or False)");
                userBoolChange = kb.nextBoolean();
                game.setMultiplayer(userBoolChange);
            }
            else if (choice ==3) {
                System.out.println(game.getGame_name()+ " :How much did the player base change");
                userPlayerChange = kb.nextInt();
                game.setPlayer_amount(userPlayerChange);
            }
            else {
                System.out.println("That option isn't available");
            }
        }
        else
        {
            System.out.println("Game doesn't exist within the database!!!");
        }



    }




}

