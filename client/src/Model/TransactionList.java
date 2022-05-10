package Model;

import java.util.ArrayList;

public class TransactionList
{

  private ArrayList<Transaction> transactions;

  public TransactionList(){
    transactions = new ArrayList<>();
  }

  public ArrayList<Transaction> getTransactions()
  {
    return transactions;
  }

  public void addTransaction(Transaction transaction){
    transactions.add(transaction);
  }

  public void removeTransaction(Transaction transaction){
    transactions.remove(transaction);
  }
}
