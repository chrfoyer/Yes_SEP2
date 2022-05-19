package databaseAdapters;

import Model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserImpl implements UserDAO
{
  private static final Object lock = new Object();
  private static UserImpl instance;

  private UserImpl() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static UserImpl getInstance() throws SQLException
  {
    if (instance == null)
    {
      synchronized (lock)
      {
        if (instance == null)
        {
          instance = new UserImpl();
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

  @Override public User create(User user) throws SQLException
  {
    User createdUser = null;
    Date bDay = Date.valueOf(user.getBday());
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO users"
              + "(username, password, email, address, name, bday, has_subscription, balance,)"
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getEmail());
      statement.setString(4, user.getAddress());
      statement.setString(5, user.getName());
      statement.setDate(6, bDay);
      statement.setBoolean(7, user.hasSubscription());
      statement.setInt(8, user.getBalance());

      statement.executeUpdate();
      statement.close();

      createdUser = readMaxId();
    }
    return createdUser;
  }

  @Override public User readById(int id) throws SQLException
  {
    User readUser = null;
    try (Connection connection = getConnection())
    {

      PreparedStatement st = connection.prepareStatement(
          "SELECT * " + "FROM users " + "WHERE id = ?;");
      st.setInt(1, id);
      ResultSet rs = st.executeQuery();

      while (rs.next())
      {
        //username, password, email, address, name, bday, has_subscription, balance,age
        String user = rs.getString("username");
        String password = rs.getString("password");
        boolean isAdmin = rs.getBoolean("is_admin");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String name = rs.getString("name");
        Date bday = rs.getDate("bday");
        boolean has_subscription = rs.getBoolean("has_subscription");
        int balance = rs.getInt("has_subscription");
        int age = rs.getInt("age");

        readUser = new User(age, user, password, isAdmin, email, address, name,
            bday.toLocalDate(), has_subscription, balance, id);

      }
      rs.close();
      st.close();
    }
    return readUser;
  }

  @Override public User readMaxId() throws SQLException
  {
    User readUser = null;
    try (Connection connection = getConnection())
    {
      Statement st = connection.createStatement();
      ResultSet rs = st.executeQuery(
          "SELECT * " + "FROM users " + "ORDER BY id DESC " + "LIMIT 1");

      while (rs.next())
      {
        //username, password, email, address, name, bday, has_subscription, balance,age
        System.out.println("Id: " + rs.getString("id"));
        int id = rs.getInt("id");
        String user = rs.getString("username");
        String password = rs.getString("password");
        boolean isAdmin = rs.getBoolean("is_admin");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String name = rs.getString("name");
        Date bday = rs.getDate("bday");
        boolean has_subscription = rs.getBoolean("has_subscription");
        int balance = rs.getInt("has_subscription");
        int age = rs.getInt("age");

        readUser = new User(age, user, password, isAdmin, email, address, name,
            bday.toLocalDate(), has_subscription, balance, id);

      }
      rs.close();
      st.close();
    }
    return readUser;
  }

  @Override public ArrayList<User> getAllUsers() throws SQLException
  {
    ArrayList<User> userArrayList = new ArrayList<>();

    try (Connection connection = getConnection())
    {
      Statement st = connection.createStatement();
      ResultSet rs = st.executeQuery(
          "SELECT * " + "FROM users " + "ORDER BY id DESC " + "LIMIT 1");

      while (rs.next())
      {
        //username, password, email, address, name, bday, has_subscription, balance,age
        System.out.println("Id: " + rs.getString("id"));
        int id = rs.getInt("id");
        String user = rs.getString("username");
        String password = rs.getString("password");
        boolean isAdmin = rs.getBoolean("is_admin");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String name = rs.getString("name");
        Date bday = rs.getDate("bday");
        boolean has_subscription = rs.getBoolean("has_subscription");
        int balance = rs.getInt("has_subscription");
        int age = rs.getInt("age");

        userArrayList.add(
            new User(age, user, password, isAdmin, email, address, name,
                bday.toLocalDate(), has_subscription, balance, id));

      }
      rs.close();
      st.close();
    }
    return userArrayList;
  }

  @Override public void update(User user) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "UPDATE users " + "SET user = ?, " + "password = ?, " + "email = ?, "
              + "name = ? " + "bday = ? " + "has_subscription = ? "
              + "balance = ? " + "WHERE id = ?;");
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getEmail());
      statement.setString(4, user.getName());
      statement.setDate(5, Date.valueOf(user.getBday()));
      statement.setBoolean(6, user.hasSubscription());
      statement.setInt(7, user.getBalance());
      statement.setInt(8, user.getId());

      statement.executeUpdate();
      statement.close();
    }
  }

  @Override public void delete(User user) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "DELETE FROM games " + "WHERE id = ? ");
      statement.setInt(1, user.getId());
      statement.executeUpdate();
      statement.close();
    }
  }
}
