package Model;

import viewmodel.LoginViewModel;

import java.io.Serializable;

/**
 * The game is the object that is rented in the system. The attributes are
 * currently just the name, but more attributes will be included according the domain model
 *
 * @author Chris,Martin,Levente,Kruno
 * @version 0.2 5/5/22
 */
public class Game implements Serializable
{
  private String name;
  private String producer;
  private String console;
  private boolean rented;
  private int daysLeft;
  private float review;
  private String esrb;

  /**
   * constructor for game
   * @param name name of game
   * @param producer producer of the game
   * @param esrb rating of the game
   */
  public Game(String name, String producer, String console, String esrb)
  {
    if (!(esrb.equals("E") || esrb.equals("E10+") || esrb.equals("T") || esrb.equals("M") || esrb.equals("AO")))
    {
      throw new IllegalArgumentException("Unknown rating");
    }
    if (!(console.equals("PC") || console.equals("PlayStation") || console.equals("Xbox") || console.equals("Nintendo")))
    {
      throw new IllegalArgumentException("Unknown console");
    }
    this.esrb = esrb;
    this.review = 3;
    this.producer = producer;
    this.name = name;
    rented = false;
    daysLeft = 0;
    this.console = console;
  }

  /**
   * Checks if obj is equals to a game
   *
   * @param obj fed into method to compare to a game
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
   * If the game ran out of days also sets rented to false
   * @author Raedrim
   */
  public void decrementDaysLeft()
  {
    if (rented)
    {
      daysLeft--;
      if (daysLeft<=0)
      {
        System.out.println(name+" Ran out of time, game not rented anymore");
      }
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
    if(rented) {
      throw new IllegalStateException("Game is already rented!");
    } else {
      this.rented = true;
      this.daysLeft = 14;
      new Transaction(this,"Rent", "User");
    }
  }

  /**
   * Sets rented to false
   */
  public void returnGame()
  {
    if (!rented) {
      throw new IllegalStateException("Game is not rented so it cannot be returned!");
    } else {
      this.rented = false;
      this.daysLeft = 0;
      new Transaction(this,"Return","User");
    }
  }

  /**
   * Gets the production company of the game
   *
   * @return production company
   */
  public String getProducer()
  {
    return producer;
  }

  /**
   * Gets the review of the game (1-5)
   *
   * @return a decimal number review of the game
   */
  public float getReview()
  {
    return review;
  }

  /**
   * Gets the international video game rating for the game
   *
   * @return String of the ESRB rating
   */
  public String getEsrb()
  {
    return esrb;
  }

  /**
   * Changes the name of the game to a new given name
   *
   * @param name new name of the game
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Changes the production house of the game to a new given production house
   *
   * @param producer new production house of the game
   */
  public void setProducer(String producer)
  {
    this.producer = producer;
  }

  /**
   * Changes the ESRB rating of the game to a new rating
   *
   * @param esrb new rating for the game
   */
  public void setEsrb(String esrb)
  {
    this.esrb = esrb;
  }

  /**
   * Changes the review rating of the game to a new given production house
   *
   * @param review new review rating for the game
   */
  public void setReview(float review)
  {
    this.review = review;
  }

  public String getConsole()
  {
    return console;
  }

  public void setConsole(String console)
  {
    this.console = console;
  }
}
