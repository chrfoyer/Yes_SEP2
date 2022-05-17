package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is the UserList class which is an arraylist of users. it contains methods for adding and removing users as well
 * as the size method and get method.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.1 11/5/22
 */
public class UserList implements Serializable
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
    if (user.getUsername().equals("admin"))
    {
      throw new IllegalArgumentException("wtf bro");
    }
    boolean found = false;
    for (int i = 0; i < users.size(); i++)
    {
      if (users.get(i).getUsername().equals(user.getUsername()))
      {
        users.remove(i);
        found = true;
      }
    }
    if (!found)
    {
      throw new IllegalArgumentException("User not found on server");
    }
  }

  //todo repleace forEach loops with a single function

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
      if (!given.getPassword().equals(foundFromList.getPassword()))
        throw new IllegalArgumentException(
            "Password does not match stored credentials");
      else
        return true;
    }
  }

  public User findUserInList(User user)
  {
    for (User temp : users)
    {
      if (temp.getUsername().equals(user.getUsername()))
        return temp;
    }
    throw new IllegalArgumentException("User does not exist on the server!");
  }

  public ArrayList<User> getUsers()
  {
    return users;
  }

  public void updateUserInfo(User oldUser, User newUser)
  {
    boolean foundOld = false;
    for (User user : users)
    {
      if (oldUser.equals(user))
      {
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());
        user.setBday(newUser.getBday());
        user.setAdmin(newUser.isAdmin());
        user.setAddress(newUser.getAddress());
        user.setHasSubscription(newUser.hasSubscription());
        foundOld = true;
      }
    }
    if (!foundOld)
      throw new IllegalArgumentException(
          "No User found on server that could be updated");
  }

  public void modifyBalance(int ammount, User user)
  {
    User temp = findUserInList(user);
    temp.modifyBalance(ammount);
  }

  public int getBalance(User user)
  {
    return findUserInList(user).getBalance();
  }

  public void payForSubscription(User user)
  {
    User temp = findUserInList(user);

    if (temp.getBalance() < 0)
      throw new IllegalStateException(
          "Users with negative balance cant pay for a subscription");
    if (temp.getBalance() < 30)
      throw new IllegalArgumentException(
          "Less than 30 money, add money to pay for subscription!");
    temp.modifyBalance(-30);
    temp.setHasSubscription(true);
  }
}
