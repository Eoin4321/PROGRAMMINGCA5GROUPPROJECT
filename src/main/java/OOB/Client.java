//Eoin set up the server client connection. Code based on multithread code we did in class.
package OOB;
import OOB.DTOs.Game_Information;
import java.io.*;
import java.net.Socket;
import java.util.*;

import OOB.example.JSonConverter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
public class Client {
    //Setting up variables for the input and output for the images.
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    //Main method
    public static void main(String[] args) {
        //Making a new instance of the client class and calling the start method to set up a new client
        Client client = new Client();
        client.start();
    }

    //This method starts the client and will connect with server.
    public void start() {

        try (   //New socket to connect to server
                Socket socket = new Socket("localhost", 8888);
                //get the socket's input and output streams, and wrap them in writer and readers
                //out sents data to the server while in takes in data.
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            //Setting up input and output streams to recieve image data.
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Client message: The Client is running and has connected to the server");
            //Setting up to take input from user.
            //Dovydas made the menu look nice
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("+--------------------------------------------------------------------+");
            System.out.print("""
                    Valid commands are:
                    Type 1 "+ID": To Display Entity by Id\s
                    Type 2: Display all Entities
                    Type 3: Add an Game to DataBase
                    Type 4: Delete Game Within The Database\s
                    Type 5 "+ID": To Get Game Image \s
                    Type 6: Exit the Client""");
            System.out.println("Please enter a command: ");
            System.out.println("+--------------------------------------------------------------------+");
            String userRequest = consoleInput.nextLine();

            //Instantiate (create) a Gson Parser
            Gson gsonParser = new Gson();

            //A while loop which will run until turned off.
            while (true) {
                out.println(userRequest);
                //Eoin wrote COMMAND 1
                //COMMAND 1 to Display Entity by Id
                //If users request starts with 1 runs this
                if (userRequest.substring(0, 1).equals("1")) {
                    //Assigning the JSON from the server to a String
                    String JsonGameId = in.readLine();
                    //Making a gameinformation object
                    Game_Information game = null;
                    //Parsing the JSON string into a gameInformation object.
                    try {
                        game = gsonParser.fromJson(JsonGameId, Game_Information.class);
                    } catch (JsonSyntaxException ex) {
                        System.out.println("Jason syntax error encountered. " + ex);
                    }
                    //Displaying game
                    displaygame(game);
                    //Eoin Wrote Command 2
                    //Command 2 will display all games
                } else if (userRequest.equals("2")) {
                    // gets response from server and then we get JSON and put it into the string
                    String JsonGameId = in.readLine();
                    //Making a game information list
                    List<Game_Information> games = new ArrayList();
                    //Parsing the Json data and assigning it to the games list
                    try {
                        games = gsonParser.fromJson(JsonGameId, List.class);
                    } catch (JsonSyntaxException ex) {
                        System.out.println("Jason syntax error encountered. " + ex);
                    }
                    //Printing games
                        System.out.println(games);
                }

                //AUTHOR LIZA WROTE THIS SECTION OF CODE
                //EOIN ADDED IN NEW PARAMETER IMAGE
                else if (userRequest.equals("3")) {
                    Game_Information newGame = new Game_Information();
                    System.out.println("Type in information to insert into database. First name");
                    newGame.setGame_name(consoleInput.nextLine());
                    System.out.println("Console");
                    newGame.setGame_console(consoleInput.nextLine());
                    System.out.println("Developer");
                    newGame.setGame_developer(consoleInput.nextLine());
                    System.out.println("Publisher");
                    newGame.setGame_publisher(consoleInput.nextLine());
                    System.out.println("Franchise");
                    newGame.setGame_franchise(consoleInput.nextLine());
                    System.out.println("Multiplayer");
                    newGame.setMultiplayer(Boolean.valueOf(consoleInput.nextLine()));
                    System.out.println("Playercount");
                    newGame.setPlayer_amount(Integer.parseInt(consoleInput.nextLine()));
                    System.out.println("Review Score");
                    newGame.setReview_Score(Integer.parseInt(consoleInput.nextLine()));
                    System.out.println("Image");
                    newGame.setImage(consoleInput.nextLine());


                    System.out.println(newGame.toString());
                    System.out.println("BREAK");
                    String gameStr = JSonConverter.gameToJson(newGame);
                    System.out.println(gameStr);

                    out.println(gameStr);
                    Game_Information game = null;
                    try {
                        String JsonGameId = in.readLine();
                        System.out.println(JsonGameId);
                        game = gsonParser.fromJson(JsonGameId, Game_Information.class);
                    } catch (JsonSyntaxException ex) {
                        System.out.println("Jason syntax error encountered. " + ex);
                    }
                    displaygame(game);
                    //Command 4 Dovydas did this command
                    //Runs this statement if request starts with 4
                } else if (userRequest.substring(0, 1).equals("4")) {
                    System.out.println(in.readLine());
                    //Command 5 to request image from server
                    //Eoin made this command
                    //If users request starts with 5 runs this.
                } else if (userRequest.substring(0, 1).equals("5")) {
                    //Running method to recieve file. Setting up name of file.
                    receiveFile("images/Recieved_image_" + in.readLine() + "_received.jpg");
                    //Command 6 To close client
                    //Eoin wrote this
                } else if (userRequest.equals("6")) {
                    //Closing socket to close the client
                    socket.close();
                    System.out.println("Client connection closed");
                    break;
                    //If request does not equal any of the above run else statement
                } else {
                    System.out.println("Not a valid user request.");
                    System.out.println("Current userrequest whicih resulted in error:" + userRequest);
                }
                //Taking in input
                consoleInput = new Scanner(System.in);
                System.out.println("Valid commands are: Type 1 to Display Entity by Id ,Type 2 to Display all Entities,Type 3 to “Add an Entity");
                System.out.println("Please enter a command: ");
                userRequest = consoleInput.nextLine();
            }
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Exiting client, Server could still be running");
    }

    //METHOD TO RECEIVE FILE based on code we did in class
    private static void receiveFile(String fileName)
            throws Exception {
        int bytes = 0;
        //Making a new fileoutputstream
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        // read the size of the file in bytes (the file length)
        long size = dataInputStream.readLong();
        System.out.println("Server: file size in bytes = " + size);


        // create a buffer to receive the incoming bytes from the socket
        byte[] buffer = new byte[4 * 1024];         // 4 kilobyte buffer

        System.out.println("Server:  Bytes remaining to be read from socket: ");

        // next, read the raw bytes in chunks (buffer size) that make up the image file
        while (size > 0 &&
                (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {

            //Writing the buffer data into a local file/
            fileOutputStream.write(buffer, 0, bytes);

            // reduce the 'size' by the number of bytes read in.
            // 'size' represents the number of bytes remaining to be read from the socket stream.
            // We repeat this until all the bytes are dealt with and the size is reduced to zero
            size = size - bytes;
            System.out.print(size + ", ");
        }

        //Sending message to say file is recieved
        System.out.println("File is Received");
        //Closing file outputstream
        fileOutputStream.close();
    }

    //METHOD TO DISPLAY GAME MADE BY DOVYDAS
    //Eoin added in Image parameters.
    public void displaygame(Game_Information game) {
        System.out.printf("+---------+--------------------+----------+-----------------+-----------------+-----------------+------------+---------------+--------------+\n" +
                        "| Game ID |        Name        | Console  |    Publisher    |    Developer    |    Franchise    | Multiplayer| Player Amount | Review Score | Image_ID \\n" +
                        "+---------+--------------------+----------+-----------------+-----------------+-----------------+------------+---------------+--------------+\n"
                        +
                        "| %-7d| %-18s| %-9s| %-16s| %-16s| %-16s| %-10b| %-13s| %-12.2f| %-18s|\n" +
                        "+---------+--------------------+----------+-----------------+-----------------+-----------------+------------+---------------+--------------+",
                game.getGameId(), game.getGame_name(), game.getGame_console(), game.getGame_publisher(), game.getGame_developer(), game.getGame_franchise(), game.getMultiplayer(), game.getPlayer_amount(), game.getReview_Score(), game.getImage());
    }

//    //DOVYDAS MADE THIS
//    private static void printInfo(List<Game_Information> dao) {
//        if (dao.isEmpty())
//            System.out.println("There is no Game anymore");
//        else {
//            for (Game_Information Game : dao)
//                System.out.println("Game: " + Game.toString());
//        }
//
//
//    }
}
