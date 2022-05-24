package viewmodel;

import Model.Transaction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * class that holds a Transaction object and relevant properties to display information
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class SimpleTransactionViewModel
{
    public ObjectProperty<Double> amount;
    public StringProperty user;
    public StringProperty type;
    public ObjectProperty<LocalDate> date;
    public Transaction transaction;

    /**
     * Constructor for creating the ViewModel
     * @param transaction Transaction to get the information from
     */
    public SimpleTransactionViewModel(Transaction transaction)
    {
        this.amount = new SimpleObjectProperty<>(transaction.getId());
        this.user = new SimpleStringProperty(transaction.getUser());
        this.type = new SimpleStringProperty(transaction.getAction());
        this.date = new SimpleObjectProperty<>(transaction.getDate());
        this.transaction = transaction;
    }

    /**
     * Gets the user from the transaction
     * @return String User
     */
    public String getUser()
    {
        return user.get();
    }


    /**
     * Gets the user from the transaction
     * @return StringProperty User
     */
    public StringProperty userProperty()
    {
        return user;
    }

    /**
     * Gets the type from the transaction
     * @return StringProperty type
     */
    public StringProperty typeProperty()
    {
        return type;
    }

    /**
     * Gets the date from the transaction
     * @return LocalDate date
     */
    public ObjectProperty<LocalDate> dateProperty()
    {
        return date;
    }

}
