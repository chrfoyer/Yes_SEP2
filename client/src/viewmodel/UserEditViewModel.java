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

public class UserEditViewModel {
  private final RemoteModel model;
  private final StringProperty usernameProperty;
  private final StringProperty passwordProperty;
  private final StringProperty nameProperty;
  private final ObjectProperty<LocalDate> dobProperty;
  private final StringProperty addressProperty;
  private final StringProperty emailProperty;
  private final StringProperty confirmProperty;
  private final StringProperty errorLabel;
  private final StringProperty balanceLabel;
  private final StringProperty fineRefundProperty;
  private final ObjectProperty<SimpleUserViewModel> selectedUserProperty;
  private final StringProperty hasSubscriptionProperty;

  /**
   * ViewModel that connects Signup to the model
   *
   * @param model RemoteModel because of RMI
   */
  public UserEditViewModel(RemoteModel model) throws RemoteException {
    this.model = model;
    usernameProperty = new SimpleStringProperty();
    passwordProperty = new SimpleStringProperty();
    nameProperty = new SimpleStringProperty();
    dobProperty = new SimpleObjectProperty<>();
    addressProperty = new SimpleStringProperty();
    emailProperty = new SimpleStringProperty();
    confirmProperty = new SimpleStringProperty();
    errorLabel = new SimpleStringProperty();
    balanceLabel = new SimpleStringProperty();
    fineRefundProperty = new SimpleStringProperty();
    hasSubscriptionProperty = new SimpleStringProperty();
    reset();

    selectedUserProperty = new SimpleObjectProperty<>();
  }

  /**
   * Getter for property
   *
   * @return usernameProperty
   */
  public StringProperty getNameProperty() {
    return nameProperty;
  }

  /**
   * Getter for property
   *
   * @return getDobProperty
   */
  public ObjectProperty<LocalDate> getDobProperty() {
    return dobProperty;
  }

  /**
   * Getter for property
   *
   * @return getAddressProperty
   */
  public StringProperty getAddressProperty() {
    return addressProperty;
  }

  /**
   * Getter for property
   *
   * @return emailProperty
   */
  public StringProperty getEmailProperty() {
    return emailProperty;
  }

  /**
   * Getter for property
   *
   * @return confirmProperty
   */
  public StringProperty getConfirmProperty() {
    return confirmProperty;
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
   * @return errorLabel
   */
  public StringProperty getErrorLabel() {
    return errorLabel;
  }

  public StringProperty getBalanceLabel() {
    return balanceLabel;
  }

  public StringProperty getFineRefundProperty() {
    return fineRefundProperty;
  }

  public StringProperty getHasSubscriptionProperty() {
    return hasSubscriptionProperty;
  }

  /**
   * Getter for property
   *
   * @return passwordProperty
   */
  public StringProperty getPasswordProperty() {
    return passwordProperty;
  }

  public void editUser() {
    try {
      if (!passwordProperty.get().equals(confirmProperty.get()))
        throw new IllegalArgumentException(
                "Passwords and confirmation have to match!");
      LocalDate dob = dobProperty.get();
      Period age = Period.between(dob, LocalDate.now());
      if (age.getYears() < 13) {
        dobProperty.set(null);
        throw new IllegalArgumentException(
                "User has to be at least 13 years old!");
      }
      if (usernameProperty.get().length() < 5)
        throw new IllegalArgumentException(
                "Username has to be at least 5 characters!");
      if (passwordProperty.get().length() < 7)
        throw new IllegalArgumentException(
                "password has to be at least 7 characters!");
      if (!emailProperty.get().contains("@"))
        throw new IllegalArgumentException("Email not in correct format!");

      User oldUser = new User(selectedUserProperty.get().getUsername(),
              selectedUserProperty.get().getPassword(),
              selectedUserProperty.get().getEmail(),
              selectedUserProperty.get().getAddress(),
              selectedUserProperty.get().getName(),
              selectedUserProperty.get().getBday());
      User newUser = selectedUserProperty.get().getUser();
      newUser = selectedUserProperty.get().getUser();
      newUser.setUsername(usernameProperty.get());
      newUser.setAddress(addressProperty.get());
      newUser.setName(nameProperty.get());
      newUser.setBday(dobProperty.get());
      newUser.setPassword(passwordProperty.get());
      newUser.setAdmin(selectedUserProperty.get().isIsAdmin());
      newUser.setEmail(emailProperty.get());
      newUser.setHasSubscription(
              selectedUserProperty.get().isHasSubscription());

      model.updateUserInfo(oldUser, newUser);

      //change finished without error
    } catch (Exception e) {
      errorLabel.set(e.getMessage());
    }
  }

  public SimpleUserViewModel getUser() {
    return selectedUserProperty.get();
  }

  public void removeUser(User user) throws RemoteException {
    try {
      model.removeUser(user);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Call this to reset the text inside the fields
   */
  public void reset() {
    try {
      if (selectedUserProperty == null) {
        usernameProperty.set("");
        passwordProperty.set("");
        nameProperty.set("");
        dobProperty.set(LocalDate.now());
        addressProperty.set("");
        emailProperty.set("");
        confirmProperty.set("");
        errorLabel.set("");
        balanceLabel.set("");
        fineRefundProperty.set("");
        hasSubscriptionProperty.set("NO DATA");
      } else {
        SimpleUserViewModel selectedUserViewModel = selectedUserProperty.get();
        usernameProperty.set(selectedUserViewModel.getUsername());
        passwordProperty.set(selectedUserViewModel.getPassword());
        nameProperty.set(selectedUserViewModel.getName());
        dobProperty.set(selectedUserViewModel.getBday());
        addressProperty.set(selectedUserViewModel.getAddress());
        emailProperty.set(selectedUserViewModel.getEmail());
        confirmProperty.set("");
        errorLabel.set("");
        balanceLabel.set(
                selectedUserProperty.get().getUser().getBalance() + "");
        fineRefundProperty.set("");
        hasSubscriptionProperty.set(
                selectedUserViewModel.isHasSubscription() ? "Yes" : "No");
      }
    } catch (Exception e) {
      errorLabel.set(e.getMessage());
      e.printStackTrace();
    }
  }

  public void setSelectedUserProperty(SimpleUserViewModel simp) {
    this.selectedUserProperty.set(simp);
  }

  /**
   * Setting user balance, either increasing or decreasing
   */
  public void fineRefund() {
    try {
      model.modifyBalance(Integer.parseInt(fineRefundProperty.get()),
              selectedUserProperty.get().getUser());
    } catch (Exception e) {
      errorLabel.set(e.getMessage());
    }
  }

  /**
   * Setting the user subscription to false;
   */
  public void revokeSubscription() {
    try {
      if (selectedUserProperty.get().getUser().hasSubscription()) {
        model.setSubscription(selectedUserProperty.get().getUser(), false);
      } else {
        throw new IllegalStateException(
                "User does not have a active subscription");
      }
    } catch (Exception e) {
      errorLabel.set(e.getMessage());

    }

  }

}
