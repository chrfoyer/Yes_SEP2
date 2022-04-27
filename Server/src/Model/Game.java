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
   * Prints out a string of the game
   *
   * @return name of game and whether the game is rented or not
   */
  public String toString()
  {
    return "Name> " + name + ":Rented: " + rented;
  }

  /**
   * Checks if obj is equals to a game
   *
   * @param obj fed into method to compaare to a game
   * @return boolean of wether it is a game or not
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Game))
    {
      return false;
    }
    Game game = (Game) obj;
    return name.equals(game.getName()) && rented == game.getRented();
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
  public boolean getRented()
  {
    return rented;
  }

  /**
   * Getter for the days left in the rental period for the game
   *
   * @return int of days left
   */
  public int getDaysLeft() {
    return daysLeft;
  }

  /**
   * Setter for the days left in the rental period for the game
   *
   * @param daysLeft in of days left in the rental per
   */
  public void setDaysLeft(int daysLeft) {
    this.daysLeft = daysLeft;
  }

  public void decrementDaysLeft() {
    daysLeft--;
  }
}
