import java.sql.*;

public class GameDAO_Impl implements GameDAO {
  private static GameDAO_Impl instance;
  private static Object lock = new Object();

  private GameDAO_Impl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized GameDAO_Impl getInstance() throws SQLException {
    if (instance == null) {
      synchronized (lock) {
        if (instance == null) {
          instance = new GameDAO_Impl();
        }
      }
    }
    return instance;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres?currentSchema=gametest",
            "postgres", "VIASEP2Y");
  }

  @Override
  public Game create(String name, String producer, String console,
                     String esrb) throws SQLException {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement(
              "INSERT INTO games(name, producer, console, esrb) VALUES (?, ?, ?, ?);");
      statement.setString(1, name);
      statement.setString(2, producer);
      statement.setString(3, console);
      statement.setString(4, esrb);
      statement.executeUpdate();
      return new Game(name, producer, console, esrb);
    }
  }

  @Override
  public Game readById(int id) throws SQLException {
    return null;
  }

  @Override
  public void update(Game game) throws SQLException {

  }

  @Override
  public void delete(Game game) throws SQLException {

  }

  public static void main(String[] args) {
    try {
      GameDAO_Impl testThing = GameDAO_Impl.getInstance();
      testThing.create("Cod", "Activision", "Xbox", "E");
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
