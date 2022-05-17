package mediator;

import Model.User;
import viewmodel.LoginViewModel;

/**
 * Class to track the currently logged-in user with static variables
 * also has ""callback"" functionalities
 *
 * @author Raedrim
 */
public class CurrentlyLoggedUser
{
  static User loggedInUser = null;
  static RemoteModel model = null;

  /**
   * Sets the loggedInUser field to the given user
   *
   * @param user the user that you want to log-in
   */
  public static void login(User user)
  {
    loggedInUser = user;
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
   * @return User
   */
  public static User getLoggedInUser()
  {
    return loggedInUser;
  }

  /**
   * Checks if the user has admin privileges
   *
   * @return boolean
   */
  public static boolean isAdmin()
  {
    return loggedInUser.isAdmin();
  }

  /**
   * Updates the user with the one on serverside
   * ALWAYS CALL AFTER FINISHING METHODS
   * basically a git push lmao
   * @exception java.rmi.RemoteException if the stars hate you
   */
  public static void updateInfoWithServer()
  {
    try
    {
      loggedInUser = model.getUserList().findUserInList(loggedInUser);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
