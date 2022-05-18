package Model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class to hold information about a single transaction. These transactions are created within the game object when
 * actions are taken upon the game, such as renting and returning. In addition, a refund or subscription payment can be
 * made without a game object.
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

  public double getAmount()
  {
    return amount;
  }

  public LocalDate getDate()
  {
    return date;
  }

  public String getType()
  {
    return type;
  }

  public String getUser()
  {
    return user;
  }

}
