//Eoin connected the server and Client based on code we did in class.
//Dovydas helped troubleshoot issue
package OOB;
import OOB.DTOs.Game_Information;
import OOB.example.JSonConverter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import OOB.DAOs.DAO;
import com.google.gson.Gson;


public class Server {
    //Setting up Dataoutput and input stream which will be used to send images from the server to the client
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    //Setting the server port number. Its final as it does not change
    final int SERVER_PORT_NUMBER = 8888;

    //making the server
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }

    public void start() throws IOException {
        //Creating server and client socket
        ServerSocket serverSocket =null;
        Socket clientSocket =null;
        try {
            //Assigning the socket to the server port number
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has started.");
            int clientNumber = 0;  //assigning a number to the client
            while (true) {
                System.out.println("Server: Listening/waiting for connections on port ..." + SERVER_PORT_NUMBER);
                //Accepting an client connection to the server socket
                clientSocket = serverSocket.accept();
                //+1 on client number to keep track of clients.
                clientNumber++;
                System.out.println("Server: Listening for connections on port ..." + SERVER_PORT_NUMBER);
                System.out.println("Server: Client " + clientNumber + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

                //Setting up the data input stream which will let the server take inputs from the client
                //The output stream allows the server to send data to the client
                dataInputStream = new DataInputStream(clientSocket.getInputStream());
                dataOutputStream = new DataOutputStream( clientSocket.getOutputStream());
                //Making a clienthandler for the client request.
                //Passing in socket and client number

                Thread t = new Thread(new ClientHandler(clientSocket, clientNumber));
                t.start();

                System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client " + clientNumber + ". ");

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        finally{
            try {
                if(clientSocket!=null)
                    clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                if(serverSocket!=null)
                    serverSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }

        }
        System.out.println("Server: Server exiting, Goodbye!");
    }
    static void sendFile(String path)
            throws Exception
    {
        int bytes = 0;
        // Open the File at the specified location (path)
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        // Sending the lenght in bytes to the client
        dataOutputStream.writeLong(file.length());

        // Breaking the file into chunks.
        byte[] buffer = new byte[4 * 1024]; // 4 kilobyte buffer

        // read bytes from file into the buffer until buffer is full or we reached end of file
        while ((bytes = fileInputStream.read(buffer))!= -1) {
            // Send the buffer contents to the client Socket, along with the count of the number of bytes
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();   // force the data into the stream
        }
        // close the file
        fileInputStream.close();
    }
}


class ClientHandler implements Runnable
{
    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket clientSocket;
    final int clientNumber;

   //Client handler
    //Constructor
    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;  // This stores the connection to the client
        this.clientNumber = clientNumber;  // ID number that we are assigning to this client
        //everytime a new client is made it is ++ so its a different number for each client.
        try {
            // The socket writer allows the server to send data to the client
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            //The socket reader allows the server to read data from the client
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        String request;
        try {
            while ((request = socketReader.readLine()) != null) {
                System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);


                //If client wants command 1 runs this etc...
                //Command 1 by Eoin. Get Game Info based on ID
                if (request.substring(0, 1).equals("1"))
                {
                    DAO dao =DAO.getInstance();
                    //Running method to get gamebyID. User types in ID after "1 " so we begin INDEX at 2
                    Game_Information gameJson = dao.getGameById(Integer.parseInt(request.substring(2)));
                    //Assigning JSON to string to send back to client
                    String gamegameJson = JSonConverter.gameToJson(gameJson);
                    //Writing to socket the JSON so the client can recieve it
                    socketWriter.println(gamegameJson);
                    System.out.println("Server Message: JSON sent to client.");
                }
                //Command 2 to display all by Eoin
                //This command sends back a JSON of a gamelist with all the data on it
                else if(request.equals("2"))
                {
                    System.out.println("Server running command 2");
                    DAO dao =DAO.getInstance();
                    List<Game_Information> games =dao.getAllGames();
                    String gameJson = JSonConverter.gameListToJson(games);
                    socketWriter.println(gameJson);
                    System.out.println("Server Message: JSON string containing list of games sent to client");
                }
                //LIZA WROTE THIS CODE
                else if (request.substring(0, 1).equals("3")) {

                    DAO dao =DAO.getInstance();
                    Gson gsonParser = new Gson();
                    Game_Information newGame = new Game_Information();
                    String json = socketReader.readLine().trim();
                    System.out.println(json);
                    newGame = gsonParser.fromJson(json, Game_Information.class);
                    dao.insertGame(newGame);
                    String gamegameJson = JSonConverter.gameToJson(newGame);
                    socketWriter.println(gamegameJson);
                }
                //Author Dovydas
                // talked it through with Eoin In class.
                else if(request.substring(0, 1).equals("4"))
                {
                    DAO dao =DAO.getInstance();
                    dao.deleteGameById(Integer.parseInt(request.substring(2)));
                    socketWriter.println("If there was a game associated with this ID it has been deleted.");
                    System.out.println("Server Message: JSON sent to client.");
                }
                //@Author Eoin
                //This command gets image based on ID
                else if(request.substring(0, 1).equals("5"))
                {
                    DAO dao =DAO.getInstance();
                    //So I use the get game Information by ID to get the game the user is searching for
                    Game_Information game = dao.getGameById(Integer.parseInt(request.substring(2)));
                    //assigning the id to a string
                    String image_id = game.getImage();
                    //I print this image name to a string and send it back to the client so they can use it for naming the file
                    socketWriter.println(image_id);
                    //Running the send file method
                    Server.sendFile("images/"+image_id);
                }

                else{
                    socketWriter.println("error I'm sorry I don't understand your request");
                    System.out.println("Your input was :"+request);
                    System.out.println("Server message: Invalid request from client.");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.socketWriter.close();
            try {
                this.socketReader.close();
                this.clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
    }

}