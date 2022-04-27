package Model;

/**
 * The game is the object that is rented in the system. The attributes are
 * currently just the name, but more attributes will be included according the domain model
 */
public class Game
{
  private String name;
  private boolean rented;
  private int daysLeft;

  /**
   * Constructor for game using 14 days as a standard rental period for now.
   *
   * @param name argument for the name of the game
   */
  public Game(String name)
  {
    this.name = name;
    rented = false;
    daysLeft = 14;
  }

  /**
   * Checks if obj is equals to a game
   *
   * @param obj fed into method to compaare to a game
   * @return boolean if the games are equal or not
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Game))
    {
      return false;
    }
    Game game = (Game) obj;
    return name.equals(game.getName()) && rented == game.isRented();
  }

  /**
   * Prints string of name of game
   *
   * @return name of game
   */
  public String getName()
  {
    return name;
  }

  /**
   * Checks if game is rented
   *
   * @return boolean of if game is rented or not
   */
  public boolean isRented()
  {
    return rented;
  }

  /**
   * Getter for the days left in the rental period for the game
   *
   * @return int of days left
   */
  public int getDaysLeft()
  {
    return daysLeft;
  }

  /**
   * Setter for the days left in the rental period for the game
   *
   * @param daysLeft in of days left in the rental per
   */
  public void setDaysLeft(int daysLeft)
  {
    this.daysLeft = daysLeft;
  }

  /**
   * Decreases the days left in the rental period. If the game is not rented, an exception is thrown.
   */
  public void decrementDaysLeft()
  {
    if (rented)
    {
      daysLeft--;
    }
    else
    {
      throw new IllegalStateException(
          "Game is not currently rented, so the days can't be decreased.");
    }
  }

  /**
   * Prints out a string of the game
   *
   * @return name of game and whether the game is rented or not
   */
  public String toString()
  {
    String str = "Name: " + name + " Rented: " + rented;
    if (rented)
    {
      str += "\nDays Left: " + daysLeft;
    }
    return str;
  }

  /**
   * Sets rented to true
   */
  public void rentGame()
  {
    this.rented = true;
  }

  /**
   * Sets rented to false
   */
  public void returnGame()
  {
    this.rented = false;
  }

}
