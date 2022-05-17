package viewmodel;

import Model.Game;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.CurrentlyLoggedUser;
import mediator.RemoteModel;

import java.util.ArrayList;

public class BalanceViewModel
{
  private RemoteModel model;
  private StringProperty balanceAmount;
  private StringProperty errorLabel;
  private StringProperty subssriptionDisplay;

  /**
   * ViewModel that connects Login to the model
   *
   * @param model RemoteModel because of RMI
   */
  public BalanceViewModel(RemoteModel model)
  {
    this.model = model;
    balanceAmount = new SimpleStringProperty();
    errorLabel = new SimpleStringProperty();
    subssriptionDisplay = new SimpleStringProperty();
  }

  /**
   * Getter for property
   *
   * @return registeredUsers
   */
  public StringProperty getBalanceAmount()
  {
    return balanceAmount;
  }

  public StringProperty getSubssriptionDisplay()
  {
    return subssriptionDisplay;
  }

  public StringProperty getErrorLabel()
  {
    return errorLabel;
  }

  /**
   * Call this to reset the text inside the fields
   */
  public void reset()
  {
    try
    {
      if (CurrentlyLoggedUser.getLoggedInUser() != null)
      {
        balanceAmount.set(
            model.getBalance(CurrentlyLoggedUser.getLoggedInUser()) + "");
        subssriptionDisplay.set(
            CurrentlyLoggedUser.getLoggedInUser().hasSubscription() ?
                "Yes" :
                "No");
      }
      //todo hopefully just debug
      else
        balanceAmount.set("NO LOGGED IN USER DETECTED");
    }
    catch (Exception e)
    {
      errorLabel.set(e.getMessage());
    }
    errorLabel.set("");
  }

  public void addFunds()
  {
    try
    {
      model.modifyBalance(30, CurrentlyLoggedUser.getLoggedInUser());
      CurrentlyLoggedUser.updateInfoWithServer();
      reset();
    }
    catch (Exception e)
    {
      errorLabel.set(e.getMessage());
    }
  }

  public void paySubscription()
  {
    try
    {
      model.payForSubscription(CurrentlyLoggedUser.getLoggedInUser());
      CurrentlyLoggedUser.updateInfoWithServer();
      reset();
    }
    catch (Exception e)
    {
      errorLabel.set(e.getMessage());
    }
  }
}
