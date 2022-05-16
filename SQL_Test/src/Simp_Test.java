import java.sql.*;

public class Simp_Test {
  public static void main(String[] args) {
    try {
      DriverManager.registerDriver(new org.postgresql.Driver());
      Connection connection = DriverManager.getConnection(
              "jdbc:postgresql://localhost:5432/postgres?currentSchema=gametest",
              "postgres", "VIASEP2Y");
      try {
        PreparedStatement insertStatement = connection.prepareStatement(
                "INSERT INTO Author (name) VALUES ('Ernest Hemingway')");
        insertStatement.executeUpdate();
      } finally {
        connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
