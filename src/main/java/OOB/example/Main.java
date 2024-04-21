package OOB.example;
import java.sql.*;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import OOB.DAOs.DAO;
import OOB.DTOs.Game_Information;
import org.ietf.jgss.GSSManager;

public class Main{
    public static void main(String[] args) throws SQLException {
        //Creating keyboard
        Scanner keyboard = new Scanner(System.in);
        //Creating Variables
        boolean menu = true;
        int choice;
        //Connecting to database. Will return unable to connect to database if cant connect
        DAO dao =DAO.getInstance();
        System.out.println("Welcome to the Video Game Database!!!");
        while(menu==true)
        {
            System.out.println("+--------------------------------------------------------------------+");
            System.out.println("Please Select An Option" +
                    "\n1.Get all Entities" +
                    "\n2.Find and Display (a single) Entity by Key" +
                    "\n3.Delete an Entity by key" +
                    "\n4.Insert an Entity" +
                    "\n5.Update an existing Entity by ID" +
                    "\n6.Get list of entities matching a filter (based on DTO object)" +
                    "\n7.Convert List of Entities to a JSON String" +
                    "\n8.Convert a single Entity by Key as a JSON String");
            choice= keyboard.nextInt();
            System.out.println("+--------------------------------------------------------------------+");

            //THis will run function 1
            //Author of Function 1 Eoin Hamill
            //Helped with by Dovydas. Helped troubleshoot why database was not connecting
            if(choice==1)
            {
                printInfo(dao.getAllGames());
            }
            //Author EOIN HAMILL
            if(choice==2)
            {
                System.out.println("TYPE IN ID YOU WANT TO SEARCH FOR ");
                int id= keyboard.nextInt();
                System.out.println(dao.getGameById(id));
            }
            //AUTHOR EOIN HAMILL
            if(choice==3) {
                System.out.println("TYPE IN ID YOU WANT TO DELETE ");
                int id= keyboard.nextInt();
                dao.deleteGameById(id);
            }
            //AUTHOR DOVYDAS JAKCUIUNAS
            if(choice==4)
            {
                Game_Information addinggame = new Game_Information();
                System.out.println("Type in information to insert into database. First name");
                addinggame.setGame_name(keyboard.next());
                System.out.println("Console");
                addinggame.setGame_console(keyboard.next());
                System.out.println("Developer");
                addinggame.setGame_developer(keyboard.next());
                System.out.println("Publisher");
                addinggame.setGame_publisher(keyboard.next());
                System.out.println("Franchise");
                addinggame.setGame_franchise(keyboard.next());
                System.out.println("Multiplayer");
                addinggame.setMultiplayer(Boolean.valueOf(keyboard.next()));
                System.out.println("Playercount");
                addinggame.setPlayer_amount(Integer.parseInt(keyboard.next()));
                System.out.println("Review Score");
                addinggame.setReview_Score(Integer.parseInt(keyboard.next()));
                System.out.println("Image");
                addinggame.setImage(keyboard.next());
                dao.insertGame(addinggame);
            }
            //AUTHOR DOVYDAS JAKCUIUNAS
            if(choice==5)
            {
                System.out.println("TYPE IN ID YOU WANT TO CHANGE ");
                int id= keyboard.nextInt();
                Game_Information game=dao.getGameById(id);
                Game_Information addinggame = new Game_Information();
                System.out.println("Type in information to insert into database. First name");
                addinggame.setGame_name(keyboard.next());
                System.out.println("Console");
                addinggame.setGame_console(keyboard.next());
                System.out.println("Developer");
                addinggame.setGame_developer(keyboard.next());
                System.out.println("Publisher");
                addinggame.setGame_publisher(keyboard.next());
                System.out.println("Franchise");
                addinggame.setGame_franchise(keyboard.next());
                System.out.println("Multiplayer");
                addinggame.setMultiplayer(Boolean.valueOf(keyboard.next()));
                System.out.println("Playercount");
                addinggame.setPlayer_amount(Integer.parseInt(keyboard.next()));
                System.out.println("Review Score");
                addinggame.setReview_Score(Integer.parseInt(keyboard.next()));
                System.out.println("Image");
                addinggame.setImage(keyboard.next());
                dao.updateGameInfo(id,addinggame);
            }
            if(choice==6)
            {
                System.out.println("CHOICE 6");
                Comparator<Game_Information> gamenameComparator = Comparator.comparing(Game_Information::getGame_name);
                System.out.println(dao.gameInformationBasedOnName(gamenameComparator));
            }
            //AUTHOR EOIN HAMILL
            if(choice==7)
            {
                List<Game_Information> game = dao.getAllGames();
                String gameJson = JSonConverter.gameListToJson(game);
                System.out.println(gameJson);

            }
            //AUTHOR EOIN HAMILL
            if(choice==8)
            {
                System.out.println("TYPE IN ID YOU WANT TO SEARCH FOR ");
                int id= keyboard.nextInt();
                Game_Information gameJson = dao.getGameById(id);
                String gamegameJson = JSonConverter.gameToJson(gameJson);
                System.out.println(gamegameJson);
            }
        }
    }

    private static void printInfo(List<Game_Information> gameInfo) {
        if( gameInfo.isEmpty() )
            System.out.println("There is no Game anymore");
        else {
            for (Game_Information Game : gameInfo)
                System.out.println("Game: " + Game.toString());
        }
    }
}