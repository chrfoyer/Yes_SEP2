package Model;

import mediator.PasswordEncryptor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is the UserList class which is an arraylist of users. It contains methods for adding and removing users as well
 * as the size method and get method.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class UserList implements Serializable
{
    private final ArrayList<User> users;

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

    /**
     * User login with encryption
     * @param username String username
     * @param password String password
     * @return boolean depending on if login was successful
     */
    public boolean login(String username, String password)
    {
        User foundFromList = null;
        for (User user : users
        )
        {
            if (user.getUsername().equals(username)) foundFromList = user;
        }


        if (foundFromList == null) throw new IllegalArgumentException("User does not exist on server");

        try
        {
            String calculatedHash = PasswordEncryptor.getEncryptedPassword(password, foundFromList.getSalt());
            System.out.println(calculatedHash);
            if (calculatedHash.equals(foundFromList.getPassword())) return true;
            throw new IllegalArgumentException("Password does not match stored credentials");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Finds a user within the user list.
     *
     * @param user The user to find
     * @return The user if they are found
     * @throws IllegalArgumentException Thrown when no user is found
     */
    public User findUserInList(User user)
    {
        for (User temp : users)
        {
            if (temp.getUsername().equals(user.getUsername()))
                return temp;
        }
        throw new IllegalArgumentException("User does not exist on the server!");
    }

    /**
     * Gets an array list with all the users
     *
     * @return All the users
     */
    public ArrayList<User> getUsers()
    {
        return users;
    }

    /**
     * @param oldUser The old version of the user
     * @param newUser The updated version of the user
     * @throws IllegalArgumentException Thrown when the user cannot be found
     * @deprecated Update the information of the user
     */
    public void updateUserInfo(User oldUser, User newUser)
    {
        boolean foundOld = false;
        for (User user : users)
        {
            if (oldUser.equals(user))
            {
                user.setName(newUser.getName());
                user.setUsername(newUser.getUsername());
                // TODO: 2022. 05. 25. make this work lol
                user.changePassword(newUser.getPassword());
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

    /**
     * Modifies the balance of a user
     *
     * @param ammount The amount to add or subtract
     * @param user    The user to modify
     */
    public void modifyBalance(int ammount, User user)
    {
        User temp = findUserInList(user);
        temp.modifyBalance(ammount);
    }

    /**
     * Get the balance of the user
     *
     * @param user The user to retrieve the balance of
     * @return The balance of the user
     */
    public int getBalance(User user)
    {
        return findUserInList(user).getBalance();
    }

    /**
     * Removes 30 balance from the user and set their subscription to active
     *
     * @param user The user to activate the subscription for
     * @throws IllegalStateException    Thrown when the balance of the user is negative
     * @throws IllegalArgumentException Thrown when the user has less than 30 balance
     */
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
