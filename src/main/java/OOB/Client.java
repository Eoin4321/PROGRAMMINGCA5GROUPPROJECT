//Eoin set up the server client connection. Code based on multithread code we did in class.
package OOB;

import OOB.DTOs.Game_Information;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {

        try (   //New socket to connect to server
                Socket socket = new Socket("localhost", 8888);
                // get the socket's input and output streams, and wrap them in writer and readers
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("Client message: The Client is running and has connected to the server");
            //Setting up to take input from user.
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("Valid commands are: Type 1 + ID Digit to Display Entity by Id ,Type 2 to Display all Entities,Type 3 to “Add an Entity");
            System.out.println("Please enter a command: ");
            String userRequest = consoleInput.nextLine();
            // Instantiate (create) a Gson Parser
            Gson gsonParser = new Gson();
            while(true) {
                // sending the command to the server on the socket
                out.println(userRequest);      // write the request to socket along with a newline terminator (which is required)

                // process the answer returned by the server
                //
                //COMMAND 1 to Display Entity by Id
                //If users request is 1
                if (userRequest.substring(0, 1).equals("1")) {
                    String JsonGameId = in.readLine();  // gets response from server and then we get JSON and put it into the string
                    //We then convert this JSON to a gameinfo object
                    //System.out.println("Client message: Response from server after \"1\" request: " + JsonGameId);
                    Game_Information game = null;
                    //Parsing the JSON string into a gameInformation object.
                    try {
                        game = gsonParser.fromJson(JsonGameId, Game_Information.class);
                    } catch (JsonSyntaxException ex) {
                        System.out.println("Jason syntax error encountered. " + ex);
                    }
                    System.out.println("Your game is " + game);
                }

                else {
                    System.out.println("Not a valid user request.");
                    System.out.println("Current userrequest whicih resulted in error:"+userRequest);
                }
                //Taking in input
                consoleInput = new Scanner(System.in);
                System.out.println("Valid commands are: Type 1 to Display Entity by Id ,Type 2 to Display all Entities,Type 3 to “Add an Entity");
                System.out.println("Please enter a command: ");
                userRequest = consoleInput.nextLine();
            }
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }

        System.out.println("Exiting client, Server could still be running");
    }
}