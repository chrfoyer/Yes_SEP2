package Model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class to hold information about a single transaction. These transactions are created within the game object when
 * actions are taken upon the game, such as renting and returning. In addition, a refund or subscription payment can be
 * made without a game object.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class Transaction implements Serializable
{
    private final int id;
    private final String user;
    private final String action;
    private final LocalDate date;

    /**
     * A 2 argument constructor creating the transaction with an id of 0 and a date of now
     *
     * @param user   The user that is doing the action
     * @param action The type of transaction occurring
     */
    public Transaction(String user, String action)
    {
        this.id = 0;
        this.action = action;
        this.user = user;
        date = LocalDate.now();
    }

    /**
     * A 4 argument constructor for the transaction.
     *
     * @param id     The serial id of the transaction
     * @param user   The user doing the action
     * @param action The type of action
     * @param date   The date that the transaction occurred
     */
    public Transaction(int id, String user, String action, LocalDate date)
    {
        this.action = action;
        this.user = user;
        this.id = id;
        this.date = date;
        TransactionList.getInstance().addTransaction(this);
    }

    /**
     * Gets the id
     *
     * @return The serial id
     */
    public double getId()
    {
        return id;
    }

    /**
     * Gets the date of the transaction
     *
     * @return The date of the transaction
     */
    public LocalDate getDate()
    {
        return date;
    }

    /**
     * Gets the type of action
     *
     * @return The type of action
     */
    public String getAction()
    {
        return action;
    }

    /**
     * Gets the username of the actor doing the action
     *
     * @return The username of the actor
     */
    public String getUser()
    {
        return user;
    }

}
