package Model;

import java.util.ArrayList;

/**
 * This is the UserList class which is an arraylist of users. it contains
 * methods for adding and removing users as well as the size method and get method.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.1 11/5/22
 */
public class UserList
{
  private ArrayList<User> users;

  /**
   * Constructor initializing an arrayList of User type.
   */
  public UserList()
  {
    users = new ArrayList<>();
  }

  /**
   * method for adding a user
   *
   * @param user user to be added to the userList
   */
  public void addUser(User user)
  {
    users.add(user);
  }

  /**
   * method for removing a user
   *
   * @param user user to be removed from the userList
   */
  public void removeUser(User user)
  {
    users.remove(user);
  }

  /**
   * method for getting the size of the userList
   *
   * @return size of userList as an int
   */
  public int size()
  {
    return users.size();
  }

  /**
   * method for getting a user at a certain index
   *
   * @param index index to fetch the user from
   * @return user at the given index
   */
  public User get(int index)
  {
    return users.get(index);
  }

  /**
   * Checks if a user is already registered
   *
   * @param given user to check against existing list
   * @return true if it contains
   */
  public boolean contains(User given)
  {
    for (User user : users)
    {
      if (user.getUsername().equals(given.getUsername()))
        return true;
    }
    return false;
  }

  public boolean login(User given)
  {
    User foundFromList = null;
    for (User user : users)
    {
      if (user.getUsername().equals(given.getUsername()))
        foundFromList = user;
    }
    if (foundFromList == null)
      throw new IllegalArgumentException("User does not exist on server");
    else
    {
      //Authenticate password
      return given.getPassword().equals(foundFromList.getPassword());
    }
  }
}
