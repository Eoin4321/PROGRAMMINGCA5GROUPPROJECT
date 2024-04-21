package OOB.example;
import java.sql.*;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import OOB.DAOs.DAO;
import OOB.DTOs.Game_Information;

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

            //This is our menu which will call methods based on input.
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
            //Author Eoin Hamill
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
    //Dovydas refactored code to methods to make it look better and easier to understand.
    //AUTHOR EOIN HAMILL wrote this code.
    //This method converts a Game_information object to json and assigns it to a string and outputs that string
    private static void gameToJsonByID(Scanner keyboard, DAO dao) throws SQLException {
        //Taking in user input
        System.out.println("TYPE IN ID YOU WANT TO SEARCH FOR ");
        int id= keyboard.nextInt();
        //making a new game-information object which = the method which gets game by ID. Giving in the user inputted ID.
        Game_Information gameJson = dao.getGameById(id);
        //ASsigning the JSON to a String
        String gamegameJson = JSonConverter.gameToJson(gameJson);
        //Printing JSON
        System.out.println(gamegameJson);
    }

    //AUTHOR EOIN HAMILL wrote this code
    //This method converts a list of games to JSON and outputs that JSON which is assigned to a string
    private static void gameListToJson(DAO dao) throws SQLException {
        //Setting up a list of games and call the get all games method to fill the list
        List<Game_Information> gameList = dao.getAllGames();
        //Assign the String to a JSON which I put my lists of games into which returns a JSON
        for (Game_Information game : gameList) {
            //Convert each game to JSON
            String gameJson = JSonConverter.gameToJson(game);
            //Print the JSON representation of each game on a new line
            System.out.println(gameJson);
        }



    }

    //EOIN HAMILL wrote this code.
    //This method finds a game based on a comparater
    private static void findGameUsingFilter(DAO dao) throws SQLException {
        System.out.println("CHOICE 6");
        //Making a comparater which compares game information names. returns 0 if equals
        Comparator<Game_Information> gamenameComparator = Comparator.comparing(Game_Information::getGame_name);
        //Calling method and printing results.
        System.out.println(dao.gameInformationBasedOnName(gamenameComparator));
    }


    //Eoin and Dovydas made this
    //This method allows the user to update game based on ID.
    //The user inputs ID they want to change and then inputs data which will then be applied to the ID they requested to change
    private static void updateGameById(Scanner keyboard, DAO dao) throws SQLException {
        System.out.println("TYPE IN ID YOU WANT TO CHANGE ");
        int id= keyboard.nextInt();
        //Making a new game which the data will be assigned to.
        //This new game will be sent through the method update game.
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
        //Buffer as it wouldnt take final input
        keyboard.nextLine();
        addinggame.setImage(keyboard.nextLine());
        //Sending game information to the updategameinfo method which will update based on changes requested.
        dao.updateGameInfo(id,addinggame);
    }


    //Author Eoin and Dovydas
    //This method searchs for a game and displays it based on ID
    private static void searchGameById(Scanner keyboard, DAO dao) throws SQLException {
        System.out.println("TYPE IN ID YOU WANT TO SEARCH FOR ");
        int id= keyboard.nextInt();
        //Takes in ID and outputs results
        System.out.println(dao.getGameById(id));
    }

    //Author eoin and Dovydas
    private static void deleteGameById(Scanner keyboard, DAO dao) throws SQLException {
        System.out.println("TYPE IN ID YOU WANT TO DELETE ");
        //Takes in ID and send it to the method which deletes a game
        int id= keyboard.nextInt();
        dao.deleteGameById(id);
        System.out.println("Game Deleted!!!!");
    }

    //Method to add a new game.
    //Author Dovydas
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

        System.out.println("Review of game:");
        int reviewIn = keyboard.nextInt();
        addingGame.setReview_Score(reviewIn);
        //Eoin added in these three lines to take in image names as well as we added this parameter later.
        System.out.println("Image_ID: ");
        //Buffer as it wouldnt take final input
        keyboard.nextLine();
        String gameimage = keyboard.nextLine();

        addingGame.setImage(gameimage);

        dao.insertGame(addingGame);
    }
    //Menu made by Dovydas
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
    //Print info made by Dovydas
    private static void printInfo(List<Game_Information> dao) {
        if( dao.isEmpty() )
            System.out.println("There is no Game anymore");
        else {
            for (Game_Information Game : dao)
                System.out.println("Game: " + Game.toString());
        }
    }
}