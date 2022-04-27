package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameListTest
{
  private GameList gameList = new GameList();
  private GameList emptyList = new GameList();

  @BeforeEach void setUp()
  {
    gameList.addGame(new Game("Cod2"));
    gameList.addGame(new Game("Cod3"));
    gameList.addGame(new Game("Cod4"));
    gameList.addGame(new Game("Cod5"));
    gameList.addGame(new Game("Cod6"));
    gameList.addGame(new Game("Cod7"));
    gameList.addGame(new Game("Cod8"));
  }

  @Test void getGame_Z()
  {
    //assertThrows(IllegalArgumentException.class, () -> gameList.getGame(null));
  }

  @Test void getGame_O()
  {
    assertDoesNotThrow(() -> gameList.getGame(new Game("Cod2")));
  }

  @Test void getGame_M()
  {
    gameList.getGame(new Game("Cod3"));
    gameList.getGame(new Game("Cod4"));
    gameList.getGame(new Game("Cod5"));

    assertDoesNotThrow(() -> gameList.getGame(new Game("Cod2")));
  }

  @Test void getGame_B()
  {
    //cant be tested
  }

  @Test void getGame_E()
  {
    //only exception tested in null
  }

  @Test void addGame_Z()
  {
    assertThrows(IllegalArgumentException.class, () -> emptyList.addGame(null));
  }

  @Test void addGame()
  {
  }

  @Test void removeGame()
  {
  }
}