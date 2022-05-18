package Model;

import java.sql.*;

public class GameDAO_Impl implements GameDAO
{
  private static GameDAO_Impl instance;
  private static Object lock = new Object();

  private GameDAO_Impl() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static GameDAO_Impl getInstance() throws SQLException
  {
    if (instance == null)
    {
      synchronized (lock)
      {
        if (instance == null)
        {
          instance = new GameDAO_Impl();
        }
      }
    }
    return instance;
  }

  private Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=gametest",
        DBKey.username, DBKey.password);
  }

  @Override public Game create(String name, String producer, String console,
      String esrb) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO games"
              + "(name, producer, console, rented, daysLeft, review, esrb, dateAdded) "
              + "VALUES (?, ?, ?, false, 14, 3.0, ?, CURRENT_DATE);");
      statement.setString(1, name);
      statement.setString(2, producer);
      statement.setString(3, console);
      statement.setString(4, esrb);
      statement.executeUpdate();
      statement.close();
      return new Game(name, producer, console, esrb);
    }
  }

  @Override public Game create(Game game) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO games"
              + "(name, producer, console, rented, daysLeft, review, esrb, dateAdded) "
              + "VALUES (?, ?, ?, false, 14, 3.0, ?, CURRENT_DATE);");
      statement.setString(1, game.getName());
      statement.setString(2, game.getProducer());
      statement.setString(3, game.getConsole());
      statement.setString(4, game.getEsrb());
      statement.executeUpdate();
      statement.close();
    }
    return game;
  }

  @Override public Game readById(int id) throws SQLException
  {
    Game readGame = null;
    try (Connection connection = getConnection())
    {
      Statement st = connection.createStatement();
      ResultSet rs = st.executeQuery(
          "SELECT * " + "FROM games " + "WHERE id = 1;");
      while (rs.next())
      {
        System.out.println("Id: " + rs.getString("id"));
        String name = rs.getString("name");
        String producer = rs.getString("producer");
        String console = rs.getString("console");
        String esrb = rs.getString("esrb");
        readGame = new Game(name, producer, console, esrb);

      }
      rs.close();
      st.close();
    }
    return readGame;
  }

  @Override public void update(Game game) throws SQLException
  {
    // This is not ideal because a game should have an id unseen by the user
    // For now, this checks the name and console, which effectively means those cannot be accurately changed
    delete(game);
    create(game);
  }

  @Override public void delete(Game game) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "DELETE FROM games " + "WHERE name = ? " + "AND console = ?;");
      statement.setString(1, game.getName());
      statement.setString(2, game.getConsole());
      statement.executeUpdate();
      statement.close();
    }

  }

  // TEST
  public static void main(String[] args)
  {
    try
    {
      GameDAO_Impl testThing = GameDAO_Impl.getInstance();
      testThing.create("Cod", "Activision", "Xbox", "E");
      testThing.create("Battlefield", "Dice", "PlayStation", "M");
      testThing.create("Rust", "FacePunch", "PC", "M");
      System.out.println(testThing.readById(1));
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }

  }
}
