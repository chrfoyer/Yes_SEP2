package viewmodel;

import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.CurrentlyLoggedUser;
import mediator.RemoteModel;

/**
 * @version 0.3
 */
public class LoginViewModel {

  private final RemoteModel model;
  private final StringProperty usernameProperty;
  private final StringProperty passwordProperty;
  private final StringProperty errorLabel;

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
        CurrentlyLoggedUser.login(user);
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

  public void logout() {
    CurrentlyLoggedUser.logout();
  }
}
