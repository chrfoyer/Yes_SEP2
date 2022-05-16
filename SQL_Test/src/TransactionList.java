import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

//a class that creates a list of transactions
public class TransactionList {

  private ArrayList<Transaction> transactions;
  private static TransactionList instance;
  private static Object lock = new Object();

  /**
   * constructor creating a arrayList of transaction type
   */
  private TransactionList() {
    transactions = new ArrayList<>();
  }

  /**
   * method getting transactions
   *
   * @return returns arrayList of transaction type
   */
  public ArrayList<Transaction> getTransactions() {
    return transactions;
  }

  /**
   * method for adding transaction to list
   *
   * @param transaction transaction to be added
   */
  public synchronized void addTransaction(Transaction transaction) {
    transactions.add(transaction);
  }

  /**
   * method for removing transaction
   *
   * @param transaction transaction to be removed
   */
  public synchronized void removeTransaction(Transaction transaction) {
    transactions.remove(transaction);
  }

  /**
   * method for getting instance of transactionlist
   *
   * @return returns instance of transactionlist
   */
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
