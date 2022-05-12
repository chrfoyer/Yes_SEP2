package viewmodel;

import Model.User;
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
 * @version 0.1
 */
public class UserProfileViewModel
{

  private RemoteModel model;
  private StringProperty usernameProperty;
  //table
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

  /**
   * Call this to reset the text inside the fields
   */
  public void reset()
  {
    usernameProperty.set("Currently logged in: "+LoginViewModel.currentlyLoggedInUser.getUsername());
    errorLabel.set("");
  }

}
