//AUTHOR EOIN HAMILL

package OOB.DTOs;

public class Game_Information {
    int GameId;
    String Game_name;
    String Game_console;
    String Game_publisher;
    String Game_developer;
    String Game_franchise;
    String Game_releasedate;
    Boolean Multiplayer;
    int Player_amount;
    int Review_Score;

    //EMPTY CONSTRUCTOR
    public Game_Information() {
    }

    //FULL CONSTRUCTOR
    public Game_Information(int gameId, String game_name, String game_console, String game_publisher,String game_developer, String game_franchise, String game_releasedate, Boolean multiplayer, int player_amount, int review_Score) {
        GameId = gameId;
        Game_name = game_name;
        Game_console = game_console;
        Game_publisher = game_publisher;
        Game_developer = game_developer;
        Game_franchise = game_franchise;
        Game_releasedate = game_releasedate;
        Multiplayer = multiplayer;
        Player_amount = player_amount;
        Review_Score = review_Score;
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

    public String getGame_releasedate() {
        return Game_releasedate;
    }

    public Boolean getMultiplayer() {
        return Multiplayer;
    }

    public int getPlayer_amount() {
        return Player_amount;
    }

    public int getReview_Score() {
        return Review_Score;
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

    public void setGame_releasedate(String game_releasedate) {
        Game_releasedate = game_releasedate;
    }

    public void setMultiplayer(Boolean multiplayer) {
        Multiplayer = multiplayer;
    }

    public void setPlayer_amount(int player_amount) {
        Player_amount = player_amount;
    }

    public void setReview_Score(int review_Score) {
        Review_Score = review_Score;
    }

    //TOSTRING

    @Override
    public String toString() {
        return "Game_Information{" +
                "GameId=" + GameId +
                ", Game_name='" + Game_name + '\'' +
                ", Game_console='" + Game_console + '\'' +
                ", Game_publisher='" + Game_publisher + '\'' +
                ", Game_developer='" + Game_developer + '\'' +
                ", Game_franchise='" + Game_franchise + '\'' +
                ", Game_releasedate='" + Game_releasedate + '\'' +
                ", Multiplayer=" + Multiplayer +
                ", Player_amount=" + Player_amount +
                ", Review_Score=" + Review_Score +
                '}';
    }
}
