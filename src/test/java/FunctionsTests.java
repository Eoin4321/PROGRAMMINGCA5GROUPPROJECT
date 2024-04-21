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

    //Derrick wrote this line of code in class
    //Before each test it runs this. This sets up our dao so we dont have to include it
    //For each test
    @BeforeEach
    public void setUP()
    {
       dao = DAO.getInstance();
    }


    //Eoin wrote this test to test if the get all games returns the correct amount of
    //games which should be 10 at the start of running the program as long as there were no
    //changes to the database.
    @Test
    void function1true() throws SQLException {
        List<Game_Information> game =new ArrayList();
        game=dao.getAllGames();
        assertEquals(game.size(),10);
    }


}
