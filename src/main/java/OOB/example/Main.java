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
            choice = mainMenu(keyboard);

            //THis will run function 1
            //Author of Function 1 Eoin Hamill
            //Helped with by Dovydas. Helped troubleshoot why database was not connecting
            if(choice==1)
            {
                printInfo(dao.getAllGames());
            }
            //Author EOIN HAMILL
            else if(choice==2)
            {
                searchGameById(keyboard, dao);
            }
            //AUTHOR EOIN HAMILL
            else if(choice==3) {
                deleteGameById(keyboard, dao);
            }
            //AUTHOR DOVYDAS JAKCUIUNAS
            else if(choice==4)
            {
                addNewGame(keyboard, dao);

            }
            //AUTHOR DOVYDAS JAKCUIUNAS
            else if(choice==5)
            {
                updateGameById(keyboard, dao);
            }
            else if(choice==6)
            {
                findGameUsingFilter(dao);
            }
            //AUTHOR EOIN HAMILL
            else if(choice==7)
            {
                gameListToJson(dao);

            }
            //AUTHOR EOIN HAMILL
            else if(choice==8)
            {
                gameToJsonByID(keyboard, dao);
            }
            else if (choice==0){
                System.out.println("Thank You For Viewing The Database!!!!!");
                System.exit(0);
            }
            else{
                System.out.println("Invalid Choice: Go Again");
            }

        }
    }

    private static void gameToJsonByID(Scanner keyboard, DAO dao) throws SQLException {
        System.out.println("TYPE IN ID YOU WANT TO SEARCH FOR ");
        int id= keyboard.nextInt();
        Game_Information gameJson = dao.getGameById(id);
        String gamegameJson = JSonConverter.gameToJson(gameJson);
        System.out.println(gamegameJson);
    }

    private static void gameListToJson(DAO dao) throws SQLException {
        List<Game_Information> game = dao.getAllGames();
        String gameJson = JSonConverter.gameListToJson(game);
        System.out.println(gameJson);
    }

    private static void findGameUsingFilter(DAO dao) throws SQLException {
        System.out.println("CHOICE 6");
        Comparator<Game_Information> gamenameComparator = Comparator.comparing(Game_Information::getGame_name);
        System.out.println(dao.gameInformationBasedOnName(gamenameComparator));
    }

    private static void updateGameById(Scanner keyboard, DAO dao) throws SQLException {
        System.out.println("TYPE IN ID YOU WANT TO CHANGE ");
        int id= keyboard.nextInt();
        Game_Information game= dao.getGameById(id);
        Game_Information addinggame = new Game_Information();
        keyboard.nextLine();
        System.out.println("Type in information to insert into database. First name");
        addinggame.setGame_name(keyboard.nextLine());
        System.out.println("Console");
        addinggame.setGame_console(keyboard.nextLine());
        System.out.println("Developer");
        addinggame.setGame_developer(keyboard.nextLine());
        System.out.println("Publisher");
        addinggame.setGame_publisher(keyboard.nextLine());
        System.out.println("Franchise");
        addinggame.setGame_franchise(keyboard.nextLine());
        System.out.println("Multiplayer");
        addinggame.setMultiplayer(keyboard.nextBoolean());
        System.out.println("Playercount");
        addinggame.setPlayer_amount(Integer.parseInt(String.valueOf(keyboard.nextInt())));


        System.out.println("Image");
        addinggame.setImage(keyboard.next());
        dao.updateGameInfo(id,addinggame);
    }

    private static void searchGameById(Scanner keyboard, DAO dao) throws SQLException {
        System.out.println("TYPE IN ID YOU WANT TO SEARCH FOR ");
        int id= keyboard.nextInt();
        System.out.println(dao.getGameById(id));
    }

    private static void deleteGameById(Scanner keyboard, DAO dao) throws SQLException {
        System.out.println("TYPE IN ID YOU WANT TO DELETE ");
        int id= keyboard.nextInt();
        dao.deleteGameById(id);
        System.out.println("Game Deleted!!!!");
    }

    private static void addNewGame(Scanner keyboard, DAO dao) throws SQLException {
        Game_Information addingGame = new Game_Information();
        System.out.println();
        keyboard.nextLine();

        System.out.println("Game Name: ");
        String gameNameIn = keyboard.nextLine();
        addingGame.setGame_name(gameNameIn);

        System.out.println("Console Where Game Began: ");
        String gameConsoleIn = keyboard.nextLine();
        addingGame.setGame_console(gameConsoleIn);

        System.out.println("Publisher of The Game: ");
        String gamePubIn = keyboard.nextLine();
        addingGame.setGame_publisher(gamePubIn);

        System.out.println("Developer of The Game: ");
        String gameDevIn = keyboard.nextLine();
        addingGame.setGame_developer(gameDevIn);

        System.out.println("Franchise which it belongs to: ");
        String gameFraIn = keyboard.nextLine();
        addingGame.setGame_franchise(gameFraIn);

        System.out.println("Is it Multiplayer (True or False): ");
        boolean multiIn = keyboard.nextBoolean();
        addingGame.setMultiplayer(multiIn);

        System.out.println("Player Amount:");
        int playerIn = keyboard.nextInt();
        addingGame.setPlayer_amount(playerIn);

        System.out.println("Review of game: (Whole number from 0-100)");
        int reviewIn = keyboard.nextInt();
        addingGame.setReview_Score(reviewIn);

        dao.insertGame(addingGame);
    }

    private static int mainMenu(Scanner keyboard) {
        int choice;
        System.out.println("+--------------------------------------------------------------------+");
        System.out.println("""
                Please Select An Option
                1.Get all Entities
                2.Find and Display (a single) Entity by Key
                3.Delete an Entity by key
                4.Insert an Entity
                5.Update an existing Entity by ID
                6.Get list of entities matching a filter (based on DTO object)
                7.Convert List of Entities to a JSON String
                8.Convert a single Entity by Key as a JSON String
                0.Exit the database""");
        choice= keyboard.nextInt();
        System.out.println("+--------------------------------------------------------------------+");
        return choice;
    }
    private static void printInfo(List<Game_Information> dao) {
        if( dao.isEmpty() )
            System.out.println("There is no Game anymore");
        else {
            for (Game_Information Game : dao)
                System.out.println("Game: " + Game.toString());
        }
    }
}