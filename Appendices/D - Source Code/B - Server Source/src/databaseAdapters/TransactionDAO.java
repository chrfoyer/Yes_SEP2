package databaseAdapters;

import Model.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * An interface to assist with accessing the database for the transaction object.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version v0.3 23/5/22
 */
public interface TransactionDAO
{
    void create(Transaction transaction) throws SQLException;

    ArrayList<Transaction> getAllTransactions() throws SQLException;
}
