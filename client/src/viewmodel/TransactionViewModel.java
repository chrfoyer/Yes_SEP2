package viewmodel;

import Model.Game;
import Model.GameList;
import Model.Transaction;
import Model.TransactionList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.RemoteModel;

import java.rmi.RemoteException;

public class TransactionViewModel {
  private RemoteModel model;
  private StringProperty errorLabel;
  final ObservableList<SimpleTransactionViewModel> data = FXCollections.observableArrayList();

  public TransactionViewModel(RemoteModel model) {
    this.model = model;
    errorLabel = new SimpleStringProperty();
    reset();
  }

  public void reset() {
    data.clear();
    TransactionList temp = null;
    try {
      TransactionList transactionList = model.getTransactionList();
      for (Transaction transaction : transactionList.getList()) {
        data.add(new SimpleTransactionViewModel(transaction));
      }
      errorLabel.set("");
    } catch (Exception e) {
      errorLabel.set(e.getMessage());
    }

  }

  public ObservableList<SimpleTransactionViewModel> getData() {
    return data;
  }

  public StringProperty getErrorLabel() {
    return errorLabel;
  }

}

