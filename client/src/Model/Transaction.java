package Model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class to hold information about a single transaction. These transactions are created within the game object when
 * actions are taken upon the game, such as renting and returning. In addition, a refund or subscription payment can be
 * made without a game object.
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class Transaction implements Serializable
{
  private final double amount;
  private final String user;
  private final String type;
  private final LocalDate date;

  //constructor for renting game/ returning game
  public Transaction(Game game, String type, String user)
  {
    amount = 0;
    this.user = user;
    this.type = type;
    date = LocalDate.now();
    TransactionList.getInstance().addTransaction(this);
  }

  //constructor for refunds
  public Transaction(String type, String user, double amount)
  {
    this.type = type;
    this.user = user;
    this.amount = amount;
    date = LocalDate.now();
    TransactionList.getInstance().addTransaction(this);
  }

  /**
   * method for getting the amount
   * @return double amount
   */
  public double getAmount()
  {
    return amount;
  }

  /**
   * method for getting the date of transaction
   * @return LocalDate
   */
  public LocalDate getDate()
  {
    return date;
  }

  /**
   * method for getting type of transaction
   * @return String type of transaction
   */
  public String getType()
  {
    return type;
  }

  /**
   * method for getting user who did the transaction
   * @return String user of who used the transaction
   */
  public String getUser()
  {
    return user;
  }

}
