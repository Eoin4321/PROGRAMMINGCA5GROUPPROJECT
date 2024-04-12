//Author Eoin Hamill
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import OOB.DTOs.Game_Information;
import OOB.DAOs.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FunctionsTests {

    DAO dao;
    @BeforeEach
    public void setUP()
    {
       dao = DAO.getInstance();
    }


    @Test
    void function1true() throws SQLException {
        List<Game_Information> game =new ArrayList();
        game=dao.getAllGames();
        assertEquals(game.size(),10);
    }

    @Test
    void function2true() throws SQLException {
        Game_Information game = new Game_Information();
        dao.getGameById(1);


        game.setGameId(3);
        game.setGame_name("Marvel Snap");
        game.setGame_console("3DS");
        game.setGame_developer("Second Dinner");
        game.setGame_franchise("Second Dinner");
        game.setGame_releasedate("2022-10-18");
        game.setMultiplayer(true);
        game.setPlayer_amount(2);
        game.setReview_Score(98);


        assertEquals(game,dao.getGameById(3));

    }

}
