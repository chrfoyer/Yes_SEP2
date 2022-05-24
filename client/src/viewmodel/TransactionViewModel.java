package viewmodel;

import Model.Transaction;
import Model.TransactionList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.RemoteModel;

/**
 * class that handles the logic for the TransactionView
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class TransactionViewModel
{
    final ObservableList<SimpleTransactionViewModel> data = FXCollections.observableArrayList();
    private final RemoteModel model;
    private final StringProperty errorLabel;

    /**
     * Constructor for a new Transaction view model.
     *
     * @param model the model we get from server
     */
    public TransactionViewModel(RemoteModel model)
    {
        this.model = model;
        errorLabel = new SimpleStringProperty();
        reset();
    }

    /**
     * resets the viewModel
     */
    public void reset()
    {
        data.clear();
        TransactionList temp = null;
        try
        {
            TransactionList transactionList = model.getTransactionList();
            for (Transaction transaction : transactionList.getList())
            {
                data.add(new SimpleTransactionViewModel(transaction));
            }
            errorLabel.set("");
        } catch (Exception e)
        {
            errorLabel.set(e.getMessage());
        }

    }

    /**
     * Gets data to fill the table
     *
     * @return the data
     */
    public ObservableList<SimpleTransactionViewModel> getData()
    {
        return data;
    }

    /**
     * Gets error label.
     *
     * @return the error label
     */
    public StringProperty getErrorLabel()
    {
        return errorLabel;
    }

}

