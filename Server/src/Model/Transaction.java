package Model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class to hold information about a single transaction. These transactions are created within the game object when
 * actions are taken upon the game, such as renting and returning. In addition, a refund or subscription payment can be
 * made without a game object.
 */
public class Transaction implements Serializable
{
    private final int id;
    private final String user;
    private final String action;
    private final LocalDate date;

    public Transaction(String user, String action)
    {
        this.id=0;
        this.action = action;
        this.user = user;
        date = LocalDate.now();
    }
    public Transaction(int id, String user, String action, LocalDate date)
    {
        this.action = action;
        this.user = user;
        this.id = id;
        this.date = date;
        TransactionList.getInstance().addTransaction(this);
    }

    public double getId()
    {
        return id;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public String getAction()
    {
        return action;
    }

    public String getUser()
    {
        return user;
    }

}
