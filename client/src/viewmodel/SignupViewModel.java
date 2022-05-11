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
public class SignupViewModel
{
  private RemoteModel model;
  private StringProperty usernameProperty;
  private StringProperty passwordProperty;
  private StringProperty nameProperty;
  private StringProperty dobProperty;
  private StringProperty addressProperty;
  private StringProperty emailProperty;
  private StringProperty confirmProperty;
  private StringProperty errorLabel;

  /**
   * ViewModel that connects Signup to the model
   *
   * @param model RemoteModel because of RMI
   */
  public SignupViewModel(RemoteModel model)
  {
    this.model = model;
    usernameProperty = new SimpleStringProperty();
    passwordProperty = new SimpleStringProperty();
    nameProperty = new SimpleStringProperty();
    dobProperty = new SimpleStringProperty();
    addressProperty = new SimpleStringProperty();
    emailProperty = new SimpleStringProperty();
    confirmProperty = new SimpleStringProperty();
    errorLabel = new SimpleStringProperty();
  }

  /**
   * Getter for property
   *
   * @return usernameProperty
   */
  public StringProperty getNameProperty()
  {
    return nameProperty;
  }

  /**
   * Getter for property
   *
   * @return getDobProperty
   */
  public StringProperty getDobProperty()
  {
    return dobProperty;
  }

  /**
   * Getter for property
   *
   * @return getAddressProperty
   */
  public StringProperty getAddressProperty()
  {
    return addressProperty;
  }

  /**
   * Getter for property
   *
   * @return emailProperty
   */
  public StringProperty getEmailProperty()
  {
    return emailProperty;
  }

  /**
   * Getter for property
   *
   * @return confirmProperty
   */
  public StringProperty getConfirmProperty()
  {
    return confirmProperty;
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
   * @return errorLabel
   */
  public StringProperty getErrorLabel()
  {
    return errorLabel;
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

  /**
   * The method that calls when you press the sign-up button
   */
  public void signup()
  {
    try
    {
      // TODO: 2022. 05. 11. Model logic validate signup
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
    nameProperty.set("");
    dobProperty.set("");
    addressProperty.set("");
    emailProperty.set("");
    confirmProperty.set("");
    errorLabel.set("");
  }
}
