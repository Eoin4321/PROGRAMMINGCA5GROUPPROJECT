
package OOB;

import OOB.DTOs.Game_Information;
import OOB.example.JSonConverter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import OOB.DAOs.DAO;
import OOB.DTOs.Game_Information;
import com.google.gson.Gson;


public class Server {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    final int SERVER_PORT_NUMBER = 8888;

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }

    public void start() throws IOException {

        ServerSocket serverSocket =null;
        Socket clientSocket =null;
        try {
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has started.");
            int clientNumber = 0;  //assigning a number to the client
            while (true) {
                System.out.println("Server: Listening/waiting for connections on port ..." + SERVER_PORT_NUMBER);
                clientSocket = serverSocket.accept();
                clientNumber++;
                System.out.println("Server: Listening for connections on port ..." + SERVER_PORT_NUMBER);
                System.out.println("Server: Client " + clientNumber + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());


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

        // send the length (in bytes) of the file to the server
        dataOutputStream.writeLong(file.length());

        // Here we break file into chunks
        byte[] buffer = new byte[4 * 1024]; // 4 kilobyte buffer

        // read bytes from file into the buffer until buffer is full or we reached end of file
        while ((bytes = fileInputStream.read(buffer))!= -1) {
            // Send the buffer contents to Server Socket, along with the count of the number of bytes
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

    // Constructor
    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;  // store socket for closing later
        this.clientNumber = clientNumber;  // ID number that we are assigning to this client
        try {
            // assign to fields
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
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
                if (request.charAt(0) == '1')
                {
                    DAO dao =DAO.getInstance();
                    Game_Information gameJson = dao.getGameById(Integer.parseInt(request.substring(2)));
                    String gamegameJson = JSonConverter.gameToJson(gameJson);
                    socketWriter.println(gamegameJson);
                    System.out.println("Server Message: JSON sent to client.");
                }
                else if(request.equals("2"))
                {
                    DAO dao =DAO.getInstance();
                    List<Game_Information> games =dao.getAllGames();
                    String gameJson = JSonConverter.gameListToJson(games);
                    socketWriter.println(gameJson);
                    System.out.println("Server Message: JSON string containing list of games sent to client");
                }
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
                else if(request.substring(0, 1).equals("4")) {
                    DAO dao =DAO.getInstance();
                    dao.deleteGameById(Integer.parseInt(request.substring(2)));
                    socketWriter.println("If there was a game associated with this ID it has been deleted.");
                    System.out.println("Server Message: JSON sent to client.");
                }

                else if(request.substring(0, 1).equals("5"))
                {
                    DAO dao =DAO.getInstance();
                    Game_Information game = dao.getGameById(Integer.parseInt(request.substring(2)));
                    String image_id = game.getImage();
                    socketWriter.println(image_id);
                    Server.sendFile("images/"+image_id);
                }

                else if (request.equals("6"))
                {

                    System.out.println("Client Terminated :" + clientNumber);
                    clientSocket.shutdownOutput();
                    clientSocket.shutdownInput();
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