package mediator;

import Model.User;
import viewmodel.LoginViewModel;

public class CurrentlyLoggedUser
{
  static User loggedInUser = null;
  static RemoteModel model = null;

  public static void login(User user)
  {
    loggedInUser = user;
  }

  public static void setModel(RemoteModel model)
  {
    CurrentlyLoggedUser.model = model;
  }

  public static void logout()
  {
    loggedInUser = null;
  }

  public static User getLoggedInUser()
  {
    return loggedInUser;
  }

  public static boolean isAdmin()
  {
    return loggedInUser.isAdmin();
  }

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
