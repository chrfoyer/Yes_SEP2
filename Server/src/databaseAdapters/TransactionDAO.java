package databaseAdapters;

import Model.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TransactionDAO
{
    Transaction create(Transaction transaction) throws SQLException;
    ArrayList<Transaction> getAllTransactions() throws SQLException;
}
