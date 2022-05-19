package viewmodel;

import Model.Transaction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class SimpleTransactionViewModel
{
    public ObjectProperty<Double> amount;
    public StringProperty user;
    public StringProperty type;
    public ObjectProperty<LocalDate> date;
    public Transaction transaction;

    public SimpleTransactionViewModel(Transaction transaction)
    {
        this.amount = new SimpleObjectProperty<>(transaction.getAmount());
        this.user = new SimpleStringProperty(transaction.getUser());
        this.type = new SimpleStringProperty(transaction.getType());
        this.date = new SimpleObjectProperty<>(transaction.getDate());
        this.transaction = transaction;
    }

    public ObjectProperty<Double> amountProperty()
    {
        return amount;
    }

    public String getUser()
    {
        return user.get();
    }

    public StringProperty userProperty()
    {
        return user;
    }

    public StringProperty typeProperty()
    {
        return type;
    }

    public ObjectProperty<LocalDate> dateProperty()
    {
        return date;
    }

}
