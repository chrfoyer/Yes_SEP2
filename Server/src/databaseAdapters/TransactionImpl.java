package databaseAdapters;

import Model.Game;
import Model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionImpl implements TransactionDAO
{
    private static Object lock = new Object();
    private static TransactionImpl instance;

    private TransactionImpl() throws SQLException
    {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

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

    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=game_test",
                DBKey.username, DBKey.password);
    }

    @Override
    public Transaction create(Transaction transaction) throws SQLException
    {
        Transaction createdTransaction = null;
        try(Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO transactions" +
                            "    (username, action, date)" +
                            "VALUES (?, ?, ?);"
            );
            ps.setString(1,transaction.getUser());
            ps.setString(2,transaction.getAction());
            ps.setDate(3, Date.valueOf(transaction.getDate()));
            ps.executeUpdate();
            ps.close();

            createdTransaction = readMaxId();
        }
        return createdTransaction;
    }

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
                readTransaction = new Transaction(id,username,action,date);
            }
            rs.close();
            st.close();
        }
        return readTransaction;
    }
    @Override
    public ArrayList<Transaction> getAllTransactions() throws SQLException
    {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        try(Connection connection = getConnection())
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
                transactionArrayList.add(new Transaction(id,username,action,date));
            }
            st.close();
            rs.close();
        }
        return null;
    }
}
