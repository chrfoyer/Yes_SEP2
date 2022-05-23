package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that holds all the transactions within an array list.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class TransactionList implements Serializable
{

    private static final Object lock = new Object();
    private static TransactionList instance;
    private ArrayList<Transaction> transactions;

    /**
     * constructor creating a arrayList of transaction type
     */
    private TransactionList()
    {
        transactions = new ArrayList<>();
    }

    /**
     * method for getting instance of transactionlist
     *
     * @return returns instance of transactionlist
     */
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

    /**
     * @param list The transaction list object containing the transactions
     * @deprecated Creates the xml file to write the transactions and then appends them in
     */
    public static void writeTransactions(TransactionList list)
    {

        File file = new File("Transactions.xml");
        try
        {
            PrintWriter out = new PrintWriter(file);

            StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"" + "standalone=\"no\"?>\n");
            ArrayList<Transaction> transactions = list.getList();
            for (int i = 0; i < list.getSize(); i++)
            {
                xml.append("\n<Transaction>");
                xml.append("\n    <Amount>").append(transactions.get(i).getId()).append("</Amount>");
                xml.append("\n    <User>").append(transactions.get(i).getUser()).append("</User>");
                xml.append("\n    <Type>").append(transactions.get(i).getAction()).append("</Type>");
                xml.append("\n    <Date>").append(transactions.get(i).getDate()).append("</Date>");

                xml.append("\n</Transaction>");
            }
            out.println(xml);
            out.close();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * method for adding transaction to list
     *
     * @param transaction transaction to be added
     */
    public synchronized void addTransaction(Transaction transaction)
    {
        transactions.add(transaction);
    }

    /**
     * method for removing transaction
     *
     * @param transaction transaction to be removed
     */
    public synchronized void removeTransaction(Transaction transaction)
    {
        transactions.remove(transaction);
    }

    /**
     * Gets the count of the transactions present in the list.
     *
     * @return The count of transactions
     */
    public int getSize()
    {
        return transactions.size();
    }

    /**
     * Gets an array list with all the transactions
     *
     * @return Array list with all the transactions
     */
    public ArrayList<Transaction> getList()
    {
        return transactions;
    }

    /**
     * Sets the transactions in the list
     *
     * @param transactions The array list to be set as the transaction list
     */
    public void setTransactions(ArrayList<Transaction> transactions)
    {
        this.transactions.clear();
        this.transactions = transactions;
    }
}
