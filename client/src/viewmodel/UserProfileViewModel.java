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
 * @version 0.3
 */
public class UserProfileViewModel
{
  private RemoteModel model;
  private StringProperty usernameProperty;
  // TODO: 2022. 05. 11. figure out how to do table
  private StringProperty errorLabel;

  /**
   * ViewModel that connects Login to the model
   *
   * @param model RemoteModel because of RMI
   */
  public UserProfileViewModel(RemoteModel model)
  {
    this.model = model;
    usernameProperty = new SimpleStringProperty();
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
    errorLabel.set("");
  }
}
