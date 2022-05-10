package Model;

import java.util.ArrayList;

public class TransactionList
{

  private ArrayList<Transaction> transactions;
  private static TransactionList instance;
  private static Object lock = new Object();

  private TransactionList()
  {
    transactions = new ArrayList<>();
  }

  public ArrayList<Transaction> getTransactions()
  {
    return transactions;
  }

  public synchronized void addTransaction(Transaction transaction)
  {
    transactions.add(transaction);
  }

  public synchronized void removeTransaction(Transaction transaction)
  {
    transactions.remove(transaction);
  }

  public static TransactionList getInstance()
  {
    if (instance == null)
    {
      synchronized (lock)
      {
        if (instance == null)
        {
          instance = new TransactionList();
        }
      }
    }
    return instance;
  }

  public int getSize(){
    return transactions.size();
  }

  public ArrayList<Transaction> getList(){
    return transactions;
  }
}
