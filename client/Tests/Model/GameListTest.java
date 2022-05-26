package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("JavaDoc")
class GameListTest
{
    private final GameList gameList = new GameList();
    private final GameList emptyList = new GameList();

    GameListTest() throws SQLException
    {
    }

    @BeforeEach
    void setUp() throws SQLException
    {
        gameList.addGame(new Game("Cod2", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod3", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod4", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod5", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod6", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod7", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod8", "Infinity Ward", "PC", "M"));
    }

    @Test
    void getGame_Z()
    {
        //assertThrows(IllegalArgumentException.class, () -> gameList.getGame());
    }

    @Test
    void getGame_O()
    {
        assertDoesNotThrow(() -> gameList.getGame(new Game("Cod2", "Infinity Ward", "PC", "M")));
    }

    @Test
    void getGame_M()
    {
        gameList.getGame(new Game("Cod3", "Infinity Ward", "PC", "M"));
        gameList.getGame(new Game("Cod4", "Infinity Ward", "PC", "M"));
        gameList.getGame(new Game("Cod5", "Infinity Ward", "PC", "M"));

        assertDoesNotThrow(() -> gameList.getGame(new Game("Cod2", "Infinity Ward", "PC", "M")));
    }

    @Test
    void getGame_B()
    {
        //cant be tested
    }

    @Test
    void getGame_E()
    {
        //only exception tested in null
    }

    @Test
    void addGame_Z()
    {
        assertThrows(IllegalArgumentException.class, () -> gameList.addGame(null));
    }

    @Test
    void addGame_O()
    {
        assertDoesNotThrow(() -> gameList.addGame(new Game("Cod the coddening", "Infinity Ward", "PC", "M")));
    }

    @Test
    void addGame_M() throws SQLException
    {
        gameList.addGame(new Game("Cod the coddening1", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod the coddening2", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod the coddening3", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod the coddening4", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod the coddening5", "Infinity Ward", "PC", "M"));
        gameList.addGame(new Game("Cod the coddening6", "Infinity Ward", "PC", "M"));
        assertDoesNotThrow(() -> gameList.addGame(new Game("Cod the coddening", "Infinity Ward", "PC", "M")));
    }

    @Test
    void removeGame_Z()
    {
        assertThrows(IllegalArgumentException.class,
                () -> gameList.removeGame((Game) null));
    }

    @Test
    void removeGame_O()
    {
        assertDoesNotThrow(() -> gameList.removeGame(new Game("Cod2", "Infinity Ward", "PC", "M")));
    }

    @Test
    void removeGame_M()
    {
        gameList.removeGame(new Game("Cod2", "Infinity Ward", "PC", "M"));
        gameList.removeGame(new Game("Cod3", "Infinity Ward", "PC", "M"));
        gameList.removeGame(new Game("Cod4", "Infinity Ward", "PC", "M"));
        gameList.removeGame(new Game("Cod5", "Infinity Ward", "PC", "M"));
        assertDoesNotThrow(() -> gameList.removeGame(new Game("Cod6", "Infinity Ward", "PC", "M")));
    }

}