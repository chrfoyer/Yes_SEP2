package Model;

import mediator.PasswordEncryptor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * This is the User class which has the constructors for creating a user. It also checks whether the user is an admin or
 * not. It has basic setters and getters
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class User implements Serializable
{

    private final int age;
    private String username;
    private String password;
    private boolean isAdmin;
    private String email;
    private String address;
    private String name;
    private LocalDate bday;
    private String salt;
    private boolean hasSubscription;
    private int balance;

    /**
     * A ten argument constructor taking in every attribute
     *
     * @param age             Age in user
     * @param username        The username used to log-in
     * @param password        The password needed to log-in
     * @param isAdmin         Shows whether the user has administrator priveledges
     * @param email           The e-mail to contact the user if needed
     * @param address         The physical mailing address to use to send the games
     * @param name            The full name of the user
     * @param bday            The date of birth of the user
     * @param hasSubscription Shows whether the user has an active subscription and can rent games
     * @param balance         The balance of money that the user has
     */
    public User(int age, String username, String password, boolean isAdmin,
                String email, String address, String name, LocalDate bday,
                boolean hasSubscription, int balance, String salt)
    {
        this.age = age;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.email = email;
        this.address = address;
        this.name = name;
        this.bday = bday;
        this.hasSubscription = hasSubscription;
        this.balance = balance;
        this.salt = salt;
    }

    /**
     * Constructor taking only string username and string password. From there, other default and null values are set.
     *
     * @param username username to be assigned to user
     * @param password password to be assigned to user
     */
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.email = null;
        this.address = null;
        this.name = null;
        this.bday = null;
        this.age = 21;
        hasSubscription = false;
        if (username.equals("admin") && password.equals("admin"))
        {
            isAdmin = true;
            hasSubscription = true;
        }
        balance = 20;
    }

    /**
     * constructor for a user who is not an admin
     *
     * @param username username to be assigned to user
     * @param password password to be assigned to user
     * @param email    email to be assigned to user
     * @param address  address to be assigned to user
     * @param name     name to be assigned to user
     * @param bday     birthday to be assigned to user
     */
    public User(String username, String password, String email, String address,
                String name, LocalDate bday,String salt)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.name = name;
        this.bday = bday;
        isAdmin = false;
        hasSubscription = false;
        balance = 30;
        age = Period.between(LocalDate.now(), bday).getYears();
        this.salt=salt;
    }

    /**
     * gets the user's username
     *
     * @return user's username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * sets user's username
     *
     * @param username what user's username will be set to
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * gets user's password
     *
     * @return user's password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * gets user's email
     *
     * @return user's email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * sets user's email
     *
     * @param email what user's email will be set to
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * gets user's address
     *
     * @return user's address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * sets user's address
     *
     * @param address what the user's address will be set to
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * gets user's name
     *
     * @return user's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * sets user's name
     *
     * @param name what the user's name will be set to
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * gets user's birthday
     *
     * @return user's birthday
     */
    public LocalDate getBday()
    {
        return bday;
    }

    /**
     * sets user's birthday
     *
     * @param bday what the user's birthday will be set to
     */
    public void setBday(LocalDate bday)
    {
        this.bday = bday;
    }

    /**
     * gets user's subscription status
     *
     * @return status of subscription
     */
    public boolean getSubscription()
    {
        return hasSubscription;
    }

    /**
     * sets the user's subscription status
     *
     * @param hasSubscription status of subscription
     */
    public void setHasSubscription(boolean hasSubscription)
    {
        this.hasSubscription = hasSubscription;
    }

    /**
     * method for finding out if a user is a admin
     *
     * @return boolean value of whether user is admin or not
     */
    public boolean isAdmin()
    {
        return isAdmin;
    }

    /**
     * sets user's admin status
     *
     * @param admin boolean deciding whether user is a admin or not
     */
    public void setAdmin(boolean admin)
    {
        isAdmin = admin;
    }

    /**
     * Gets a true or false value based on the users active subscription
     *
     * @return boolean
     * @throws IllegalStateException if balance is below 0
     */
    public boolean hasSubscription()
    {
        if (balance < 0)
            return false;
        return hasSubscription;
    }

    /**
     * gets the user's balance
     *
     * @return int balance of user
     */
    public int getBalance()
    {
        return balance;
    }

    /**
     * gets the user's age
     *
     * @return int age of user
     */
    public int getAge()
    {
        return age;
    }

    /**
     * Method to increase or decrease user balance
     *
     * @param ammount integer works with negative values
     */
    public void modifyBalance(int ammount)
    {
        this.balance += ammount;
    }

    /**
     * checks if an object is equals to user
     *
     * @param obj to be compared to user
     * @return boolean true or false of whether they are equal
     */
    public boolean equals(Object obj)
    {
        if (!(obj instanceof User))
        {
            return false;
        }
        User user = (User) obj;
        return (user.getUsername().equals(this.getUsername()));
    }

    public String getSalt()
    {
        return salt;
    }

    public void changePassword(String newPassword)
    {
        try
        {
            salt = PasswordEncryptor.getNewSalt();
            password = PasswordEncryptor.getEncryptedPassword(newPassword, salt);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
