//AUTHOR EOIN HAMILL
//Basic code to set up a Game_Information object with getters and setters and a toString.
//Includes String, int boolean and double.

package OOB.DTOs;

public class Game_Information {
    int GameId;
    String Game_name;
    String Game_console;
    String Game_publisher;
    String Game_developer;
    String Game_franchise;
    Boolean Multiplayer;
    int Player_amount;
    double Review_Score;
    String Image_ID;

    //EMPTY CONSTRUCTOR
    public Game_Information() {
    }

    //FULL CONSTRUCTOR
    public Game_Information(int gameId, String game_name, String game_console, String game_publisher,String game_developer, String game_franchise,Boolean multiplayer, int player_amount, int review_Score, String image_id) {
        GameId = gameId;
        Game_name = game_name;
        Game_console = game_console;
        Game_publisher = game_publisher;
        Game_developer = game_developer;
        Game_franchise = game_franchise;
        Multiplayer = multiplayer;
        Player_amount = player_amount;
        Review_Score = review_Score;
        Image_ID=image_id;

    }
    //PARTIAL CONSTRUCTOR FOR FUNCTION 6 to filter Entities

    public Game_Information(String game_name) {
        Game_name = game_name;
    }



    //GETTERS


    public String getGame_publisher() {
        return Game_publisher;
    }

    public int getGameId() {
        return GameId;
    }

    public String getGame_name() {
        return Game_name;
    }

    public String getGame_console() {
        return Game_console;
    }

    public String getGame_developer() {
        return Game_developer;
    }

    public String getGame_franchise() {
        return Game_franchise;
    }

    public Boolean getMultiplayer() {
        return Multiplayer;
    }

    public int getPlayer_amount() {
        return Player_amount;
    }

    public double getReview_Score() {
        return Review_Score;
    }

    public String getImage() {
        return Image_ID;
    }

    //SETTERS

    public void setGame_publisher(String game_publisher) {
        Game_publisher = game_publisher;
    }

    public void setGameId(int gameId) {
        GameId = gameId;
    }

    public void setGame_name(String game_name) {
        Game_name = game_name;
    }

    public void setGame_console(String game_console) {
        Game_console = game_console;
    }

    public void setGame_developer(String game_developer) {
        Game_developer = game_developer;
    }

    public void setGame_franchise(String game_franchise) {
        Game_franchise = game_franchise;
    }

    public void setMultiplayer(Boolean multiplayer) {
        Multiplayer = multiplayer;
    }

    public void setPlayer_amount(int player_amount) {
        Player_amount = player_amount;
    }

    public void setReview_Score(double review_Score) {
        Review_Score = review_Score;
    }

    public void setImage(String image_ID) {
        Image_ID = image_ID;
    }

    //toString Method - Basic


    @Override
    public String toString() {
        return "Game_Information{" +
                "GameId=" + GameId +
                ", Game_name='" + Game_name + '\'' +
                ", Game_console='" + Game_console + '\'' +
                ", Game_publisher='" + Game_publisher + '\'' +
                ", Game_developer='" + Game_developer + '\'' +
                ", Game_franchise='" + Game_franchise + '\'' +
                ", Multiplayer=" + Multiplayer +
                ", Player_amount=" + Player_amount +
                ", Review_Score=" + Review_Score +
                ", Image='" + Image_ID + '\'' +
                '}';
    }
}