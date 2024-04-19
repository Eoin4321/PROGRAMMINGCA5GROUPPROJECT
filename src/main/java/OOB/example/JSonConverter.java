package OOB.example;
import OOB.DTOs.Game_Information;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jdk.jshell.spi.SPIResolutionException;


import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JSonConverter{

    public static String gameListToJson(List<Game_Information> game)
    {
        Gson gsonParser = new Gson();
        String gameJson = gsonParser.toJson(game);
        return gameJson;
    }



    public static String gameToJson(Game_Information game)
    {
        Gson gsonParser = new Gson();
        String gameJson = gsonParser.toJson(game);
        return gameJson;
    }


}

