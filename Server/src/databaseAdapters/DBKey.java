package databaseAdapters;

/**
 * Class for storing access key to database
 * when server runs we set the values
 * @author Chris, Martin, Levente, Kruno
 */
public class DBKey
{
    public static String username = "";
    public static String password = "";

    /**
     * Setting database password
     * @param password password for database
     */
    public static void setPassword(String password)
    {
        DBKey.password = password;
    }
    /**
     * Setting database username
     * @param username username for database
     */
    public static void setUsername(String username)
    {
        DBKey.username = username;
    }
}
