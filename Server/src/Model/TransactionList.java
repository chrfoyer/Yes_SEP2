package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TransactionList {

  private ArrayList<Transaction> transactions;
  private static TransactionList instance;
  private static Object lock = new Object();

  private TransactionList() {
    transactions = new ArrayList<>();
  }

  public ArrayList<Transaction> getTransactions() {
    return transactions;
  }

  public synchronized void addTransaction(Transaction transaction) {
    transactions.add(transaction);
  }

  public synchronized void removeTransaction(Transaction transaction) {
    transactions.remove(transaction);
  }

  public static TransactionList getInstance() {
    if (instance == null) {
      synchronized (lock) {
        if (instance == null) {
          instance = new TransactionList();
        }
      }
    }
    return instance;
  }

  public int getSize() {
    return transactions.size();
  }

  public static void writeTransactions(TransactionList list) {

    File file = new File("Transactions.xml");
    try {
      PrintWriter out = new PrintWriter(file);

      String xml = "";
      xml +=
              "<?xml version=\"1.0\" encoding=\"UTF-8\"" + "standalone=\"no\"?>\n";
      ArrayList<Transaction> transactions = list.getList();
      for (int i = 0; i < list.getSize(); i++) {
        xml += "\n<Transaction>";
        xml +=
                "\n    <Amount>" + transactions.get(i).getAmount() + "</Amount>";
        xml += "\n    <User>" + transactions.get(i).getUser() + "</User>";
        xml += "\n    <Type>" + transactions.get(i).getType() + "</Type>";
        xml += "\n    <Date>" + transactions.get(i).getDate() + "</Date>";

        xml += "\n</Transaction>";
      }
      out.println(xml);
      out.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Transaction> getList() {
    return transactions;
  }
}
