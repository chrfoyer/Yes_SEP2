package databaseAdapters;

import Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class that implements the CRUD functionality of the UserDAO
 *
 * @author Chris, Martin, Levente, Kruno
 * @version v0.3 23/5/22
 */
public class UserImpl implements UserDAO
{
    private static final Object lock = new Object();
    private static UserImpl instance;

    /**
     * Private constructor for singleton
     *
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    private UserImpl() throws SQLException
    {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    /**
     * Singleton instance getter
     *
     * @return The instance of the user implementation
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
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

    /**
     * Creates datrabse connection
     *
     * @return Connection
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=game_test",
                DBKey.username, DBKey.password);
    }

    /**
     * Creates a new user using the database
     *
     * @param user User to pass down to database
     * @return user that we got from database
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public User create(User user) throws SQLException
    {
        User createdUser = null;
        Date bDay = Date.valueOf(user.getBday());
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users"
                            + "(username, password, email, address, name, bday, has_subscription, balance, is_admin, salt)"
                            + "VALUES (?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?);");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getName());
            statement.setDate(6, bDay);
            statement.setBoolean(7, user.hasSubscription());
            statement.setInt(8, user.getBalance());
            statement.setBoolean(9, user.isAdmin());
            statement.setString(10, user.getSalt());

            statement.executeUpdate();
            statement.close();

            createdUser = readUsername(user.getUsername());
        }
        return createdUser;
    }

    /**
     * Finds an user in the database based on the username (the primary key)
     *
     * @param username String username
     * @return User or null
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public User readUsername(String username) throws SQLException
    {
        User readUser = null;
        try (Connection connection = getConnection())
        {

            PreparedStatement st = connection.prepareStatement(
                    "SELECT * " + "FROM users " + "WHERE username = ?;");
            st.setString(1, username);
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
                int balance = rs.getInt("balance");
                int age = rs.getInt("age");
                String salt = rs.getString("salt");

                readUser = new User(age, user, password, isAdmin, email, address, name,
                        bday.toLocalDate(), has_subscription, balance, salt);

            }
            rs.close();
            st.close();
        }
        return readUser;
    }

    /**
     * Gets every use from the database
     *
     * @return Array list of all users in the database
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public ArrayList<User> getAllUsers() throws SQLException
    {
        ArrayList<User> userArrayList = new ArrayList<>();

        try (Connection connection = getConnection())
        {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * " + "FROM users ");

            while (rs.next())
            {
                //username, password, email, address, name, bday, has_subscription, balance,age
                String user = rs.getString("username");
                String password = rs.getString("password");
                boolean isAdmin = rs.getBoolean("is_admin");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String name = rs.getString("name");
                Date dateSQL = rs.getDate("bday");
                boolean has_subscription = rs.getBoolean("has_subscription");
                int balance = rs.getInt("balance");
                int age = rs.getInt("age");
                String salt = rs.getString("salt");

                LocalDate bday = null;
                if (dateSQL != null) bday = dateSQL.toLocalDate();

                userArrayList.add(
                        new User(age, user, password, isAdmin, email, address, name,
                                bday, has_subscription, balance, salt));

            }
            rs.close();
            st.close();
        }
        return userArrayList;
    }

    /**
     * Updates an existing User with new information
     *
     * @param user User with new data
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public void update(User user) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            Date date = null;
            if (user.getBday() != null)
            {
                date = Date.valueOf(user.getBday());
            }
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users " + "SET password = ?, " + "email = ?, " + "name = ?, "
                            + "bday = ?, " + "has_subscription = ?, " + "balance = ?, " + "salt = ?"
                            + "WHERE username = ?;");
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getName());
            statement.setDate(4, date);
            statement.setBoolean(5, user.hasSubscription());
            statement.setInt(6, user.getBalance());
            statement.setString(7, user.getSalt());

            statement.setString(8, user.getUsername());

            statement.executeUpdate();
            statement.close();
        }
    }

    /**
     * Deletes User from database
     *
     * @param user User to be deleted
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public void delete(User user) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users " + "WHERE username = ?");
            statement.setString(1, user.getUsername());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //TODO  fix statement
    @Override
    public boolean hasRental(User user) throws SQLException
    {
        try
        {
            int count = 0;
            try (Connection connection = getConnection())
            {
                PreparedStatement st = connection.prepareStatement(
                        "SELECT COUNT(*) " +
                                "FROM rentals " +
                                "WHERE username = ? " +
                                "AND active = TRUE;");
           /* ResultSet rs = st.executeQuery("SELECT active(*) " +
                    "FROM rentals " +
                    "WHERE username = '" + user.getUsername() + "'");
*/
                st.setString(1, user.getUsername());
                System.out.println(st);
                ResultSet rs = st.executeQuery();
                while (rs.next())
                {
                    // get count of rentals
                    count = rs.getInt("count");
                /*
                String active = rs.getString("active");
                if (active.equals("true")){
                    return true;
                }
                 */
                }
                rs.close();
                st.close();
                return count != 0;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;

    }
}
