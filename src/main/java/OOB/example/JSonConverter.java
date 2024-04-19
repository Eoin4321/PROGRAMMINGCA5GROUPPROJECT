package OOB.example;
import OOB.DTOs.Game_Information;
import com.google.gson.Gson;
import java.util.List;
//Author Eoin Hamill
//Made a Json converter class which will send Game_Information objects and Game_Information lists
//which will then convert them into a json format.
//This will allow me to send data from server to client
public class JSonConverter{

    //A method to convert Game_Information lists to json
    public static String gameListToJson(List<Game_Information> game)
    {
        //Creating Gson Object which will be used to convert list to string.
        Gson gsonParser = new Gson();
        //Turing the game list objects using gson.toJson which is then assigned to a string
        String gameListJson = gsonParser.toJson(game);
        //returning that string
        return gameListJson;
    }

    public static String gameToJson(Game_Information game)
    {
        //Creating Gson Object which will be used to convert list to string.
        Gson gsonParser = new Gson();
        //Turing the game object using gson.toJson which is then assigned to a string
        String gameJson = gsonParser.toJson(game);
        //returning that string
        return gameJson;
    }


}