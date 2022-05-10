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
  }
  //constructor for refunds
  public Transaction(String type, String user, double amount){
    this.type = type;
    this.user = user;
    this.amount = amount;
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

  public void setAmount(double amount)
  {
    this.amount = amount;
  }

  public void setDate(LocalDate date)
  {
    this.date = date;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public void setUser(String user)
  {
    this.user = user;
  }
}
