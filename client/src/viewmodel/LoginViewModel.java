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
 * @version 0.3
 */
public class LoginViewModel {
  /**
   * Gets the currently logged-in user
   */
  public static User currentlyLoggedInUser;

  private RemoteModel model;
  private StringProperty usernameProperty;
  private StringProperty passwordProperty;
  private StringProperty errorLabel;

  /**
   * ViewModel that connects Login to the model
   *
   * @param model RemoteModel because of RMI
   */
  public LoginViewModel(RemoteModel model) {
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
  public StringProperty getUsernameProperty() {
    return usernameProperty;
  }

  /**
   * Getter for property
   *
   * @return passwordProperty
   */
  public StringProperty getPasswordProperty() {
    return passwordProperty;
  }

  public StringProperty getErrorLabel() {
    return errorLabel;
  }

  public boolean login() {
    try {
      // TODO: 2022. 05. 11. Model logic validate login
      if (getUsernameProperty().get().equals(""))
        throw new IllegalArgumentException("Username cant be empty");
      if (getPasswordProperty().get().equals(""))
        throw new IllegalArgumentException("Password cant be empty");
      User user = new User(getUsernameProperty().getValue(),
              getPasswordProperty().getValue());
      if (model.login(user)) {
        currentlyLoggedInUser = user;
        return true;
      }
      return false;
    } catch (Exception e) {
      errorLabel.set(e.getMessage());
    }
    return false;
  }

  /**
   * Call this to reset the text inside the fields
   */
  public void reset() {
    usernameProperty.set("");
    passwordProperty.set("");
    errorLabel.set("");
  }

  /**
   * Call this to set loggedInUser to null
   */
  public static void logout() {
    currentlyLoggedInUser = null;
  }
}
