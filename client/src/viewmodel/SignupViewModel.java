package viewmodel;

import Model.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.RemoteModel;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.Period;

/**
 * @version 0.3
 */
public class SignupViewModel
{
  private final RemoteModel model;
  private final StringProperty usernameProperty;
  private final StringProperty passwordProperty;
  private final StringProperty nameProperty;
  private final ObjectProperty<LocalDate> dobProperty;
  private final StringProperty addressProperty;
  private final StringProperty emailProperty;
  private final StringProperty confirmProperty;
  private final StringProperty errorLabel;

  /**
   * ViewModel that connects Signup to the model
   *
   * @param model RemoteModel because of RMI
   */
  public SignupViewModel(RemoteModel model) throws RemoteException
  {
    this.model = model;
    usernameProperty = new SimpleStringProperty();
    passwordProperty = new SimpleStringProperty();
    nameProperty = new SimpleStringProperty();
    dobProperty = new SimpleObjectProperty<>();
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
  public ObjectProperty<LocalDate> getDobProperty()
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
  public boolean signup()
  {
    try
    {
      if (!passwordProperty.get().equals(confirmProperty.get()))
        throw new IllegalArgumentException(
                "Passwords and confirmation have to match!");
      LocalDate dob = dobProperty.get();
      Period age = Period.between(dob, LocalDate.now());
      /*
       if (age.getYears() < 13)
      {
        dobProperty.set(null);
        throw new IllegalArgumentException(
                "User has to be at least 13 years old!");
      }
       */
      if (usernameProperty.get().length() < 5)
        throw new IllegalArgumentException(
                "Username has to be at least 5 characters!");
      if (passwordProperty.get().length() < 7)
        throw new IllegalArgumentException(
                "password has to be at least 7 characters!");
      if (!emailProperty.get().contains("@"))
        throw new IllegalArgumentException("Email not in correct format!");
      User user = new User(usernameProperty.get(), passwordProperty.get(),
              emailProperty.get(), addressProperty.get(), nameProperty.get(), dob);
      model.signup(user);
      return true;
    } catch (Exception e)
    {
      errorLabel.set(e.getMessage());
    }
    return false;
  }

  /**
   * Call this to reset the text inside the fields
   */
  public void reset()
  {
    usernameProperty.set("");
    passwordProperty.set("");
    nameProperty.set("");
    dobProperty.set(LocalDate.now());
    addressProperty.set("");
    emailProperty.set("");
    confirmProperty.set("");
    errorLabel.set("");
  }
}
