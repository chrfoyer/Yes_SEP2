package databaseAdapters;

import Model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A class implementing the transaction data access object. Statements are prepared and then executed on the database.
 * The singleton design pattern is used to avoid registering the driver too many times.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version v0.3 23/5/22
 */
public class TransactionImpl implements TransactionDAO
{
    private static final Object lock = new Object();
    private static TransactionImpl instance;

    /**
     * A zero argument private constructor creating the object by registering the driver.
     *
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    private TransactionImpl() throws SQLException
    {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    /**
     * A static method returning the instance of the object. The private constructor is called when the instance is
     * null.
     *
     * @return The instance of the transaction implementation
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    public static TransactionDAO getInstance() throws SQLException
    {
        if (instance == null)
        {
            synchronized (lock)
            {
                if (instance == null)
                {
                    instance = new TransactionImpl();
                }
            }
        }
        return instance;
    }

    /**
     * A method for returning the connection to the database. It uses the credentials held within the DBKey class to
     * authenticate.
     *
     * @return The connection to the database
     * @throws SQLException Thrown when the credentials or url do not match the database
     */
    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=game_test",
                DBKey.username, DBKey.password);
    }

    /**
     * Inserts the transaction into the database and returns it.
     *
     * @param transaction The transaction to insert
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public void create(Transaction transaction) throws SQLException
    {
        Transaction createdTransaction = null;
        try (Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO transactions" +
                            "    (username, action, date)" +
                            "VALUES (?, ?, ?);"
            );
            ps.setString(1, transaction.getUser());
            ps.setString(2, transaction.getAction());
            ps.setDate(3, Date.valueOf(transaction.getDate()));
            ps.executeUpdate();
            ps.close();

            createdTransaction = readMaxId();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns the most recent transaction with the highest serial id.
     *
     * @return The most recent transaction
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    public Transaction readMaxId() throws SQLException
    {
        Transaction readTransaction = null;
        try (Connection connection = getConnection())
        {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * " +
                            "FROM transactions " +
                            "ORDER BY id DESC " +
                            "LIMIT 1");
            while (rs.next())
            {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String action = rs.getString("action");
                Date dateSQL = rs.getDate("date");
                LocalDate date = null;
                if (dateSQL != null)
                {
                    date = dateSQL.toLocalDate();
                }
                readTransaction = new Transaction(id, username, action, date);
            }
            rs.close();
            st.close();
        }
        return readTransaction;
    }

    /**
     * A method to return all the transactions held within the database.
     *
     * @return An array list of all transactions
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public ArrayList<Transaction> getAllTransactions() throws SQLException
    {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT *" +
                            "FROM transactions;"
            );
            while (rs.next())
            {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String action = rs.getString("action");
                Date dateSQL = rs.getDate("date");
                LocalDate date = null;
                if (dateSQL != null)
                {
                    date = dateSQL.toLocalDate();
                }
                transactionArrayList.add(new Transaction(id, username, action, date));
            }
            st.close();
            rs.close();
        }
        return transactionArrayList;
    }
}
