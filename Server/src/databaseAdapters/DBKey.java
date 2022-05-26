package databaseAdapters;

public class DBKey
{
    public static String username = "";
    public static String password = "";

    public static void setPassword(String password)
    {
        DBKey.password = password;
    }

    public static void setUsername(String username)
    {
        DBKey.username = username;
    }
}
