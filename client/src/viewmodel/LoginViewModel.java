package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import mediator.RemoteModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @version 0.2
 */
public class LoginViewModel
{
  private RemoteModel model;
  private StringProperty usernameProperty;
  private StringProperty passwordProperty;
  private StringProperty errorLabel;

  /**
   * ViewModel that connects Login to the model
   *
   * @param model RemoteModel because of RMI
   */
  public LoginViewModel(RemoteModel model)
  {
    this.model = model;
    usernameProperty = new SimpleStringProperty();
    passwordProperty = new SimpleStringProperty();
    errorLabel = new SimpleStringProperty();
  }

  /**
   * Getter for property
   *
   * @return usernameProperty
   */
  public StringProperty getUsernameProperty()
  {
    return usernameProperty;
  }

  /**
   * Getter for property
   *
   * @return passwordProperty
   */
  public StringProperty getPasswordProperty()
  {
    return passwordProperty;
  }

  public StringProperty getErrorLabel()
  {
    return errorLabel;
  }

  public void login()
  {
    try
    {
      // TODO: 2022. 05. 11. Model logic validate login
    }
    catch (Exception e)
    {
      errorLabel.set(e.getMessage());
    }
  }

  /**
   * Call this to reset the text inside the fields
   */
  public void reset()
  {
    usernameProperty.set("");
    passwordProperty.set("");
    errorLabel.set("");
  }
}
