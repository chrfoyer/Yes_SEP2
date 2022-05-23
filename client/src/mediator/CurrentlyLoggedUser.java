package mediator;

import Model.User;
import Model.UserList;

import java.rmi.RemoteException;

/**
 * Class to track the currently logged-in user with static variables also has ""callback"" functionalities
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class CurrentlyLoggedUser
{
    static User loggedInUser = null;
    static RemoteModel model = null;

    /**
     * Sets the loggedInUser field to the given user
     *
     * @param user the user that you want to log-in
     * @throws RemoteException if something goes wrong server
     */
    public static void login(User user) throws RemoteException
    {
        UserList userList = model.getUserList();
        loggedInUser = userList.findUserInList(user);
    }

    /**
     * Sets the model to interact with the server
     *
     * @param model is the server
     */
    public static void setModel(RemoteModel model)
    {
        CurrentlyLoggedUser.model = model;
    }

    /**
     * Sets loggedInUser to null
     */
    public static void logout()
    {
        loggedInUser = null;
    }

    /**
     * gets the logged-in user
     *
     * @return User that is logged in
     */
    public static User getLoggedInUser()
    {
        return loggedInUser;
    }

    /**
     * Checks if the user has admin privileges
     *
     * @return boolean of whether is the user is an admin
     */
    public static boolean isAdmin()
    {
        updateInfoWithServer();
        return loggedInUser.isAdmin();

    }

    /**
     * Updates the user with the one on serverside
     *
     * @hidden basically a git push lmao
     */
    public static void updateInfoWithServer()
    {
        try
        {
            loggedInUser = model.getUserList().findUserInList(loggedInUser);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
