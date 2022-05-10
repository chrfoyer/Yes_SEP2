package Model;

import java.time.LocalDate;

public class Transaction
{
  private double amount;
  private String user;
  private String type;
  private LocalDate date;

  //constructor for renting game/ returning game
  public Transaction(Game game, String type, String user ){
    amount = 0;
    this.user = user;
    this.type = type;
    date = LocalDate.now();
    TransactionList.getInstance().addTransaction(this);
  }
  //constructor for refunds
  public Transaction(String type, String user, double amount){
    this.type = type;
    this.user = user;
    this.amount = amount;
    TransactionList.getInstance().addTransaction(this);
  }

  public double getAmount(){
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
