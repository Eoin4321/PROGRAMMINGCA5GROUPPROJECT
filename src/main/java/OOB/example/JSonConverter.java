package OOB.example;
import OOB.DTOs.Game_Information;
import com.google.gson.Gson;


import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JSonConverter{

    public static String gameListToJson(List<Game_Information> game)
    {
        Gson gsonParser = new Gson();
        String gameListJson = gsonParser.toJson(game);
        return gameListJson;
    }

}

