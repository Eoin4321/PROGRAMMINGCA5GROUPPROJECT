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


}
