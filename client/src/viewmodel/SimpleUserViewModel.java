package viewmodel;

import Model.User;
import javafx.beans.property.*;

import java.time.LocalDate;

public class SimpleUserViewModel {

  private StringProperty username;
  private StringProperty password;
  private BooleanProperty isAdmin;
  private StringProperty email;
  private StringProperty address;
  private StringProperty name;
  private ObjectProperty<LocalDate> bday;
  private BooleanProperty hasSubscription;
  private IntegerProperty age;
  private User user;

  public SimpleUserViewModel(User user) {
    username = new SimpleStringProperty(user.getUsername());
    password = new SimpleStringProperty(user.getPassword());
    isAdmin = new SimpleBooleanProperty(user.isAdmin());
    email = new SimpleStringProperty(user.getEmail());
    address = new SimpleStringProperty(user.getAddress());
    name = new SimpleStringProperty(user.getName());
    bday = new SimpleObjectProperty<>(user.getBday());
    hasSubscription = new SimpleBooleanProperty(user.hasSubscription());
    this.user = user;
    // TODO: 13/05/2022
    // age = new SimpleIntegerProperty(user....)
  }

  public String getUsername() {
    return username.get();
  }

  public StringProperty usernameProperty() {
    return username;
  }

  public String getPassword() {
    return password.get();
  }

  public StringProperty passwordProperty() {
    return password;
  }

  public boolean isIsAdmin() {
    return isAdmin.get();
  }

  public BooleanProperty isAdminProperty() {
    return isAdmin;
  }

  public String getEmail() {
    return email.get();
  }

  public StringProperty emailProperty() {
    return email;
  }

  public String getAddress() {
    return address.get();
  }

  public StringProperty addressProperty() {
    return address;
  }

  public String getName() {
    return name.get();
  }

  public StringProperty nameProperty() {
    return name;
  }

  public LocalDate getBday() {
    return bday.get();
  }

  public ObjectProperty<LocalDate> bdayProperty() {
    return bday;
  }

  public StringProperty bdayStringProperty() {
    return new SimpleStringProperty(bday.get().toString());
  }

  public boolean isHasSubscription() {
    return hasSubscription.get();
  }

  public BooleanProperty hasSubscriptionProperty() {
    return hasSubscription;
  }

  public int getAge() {
    return age.get();
  }

  public IntegerProperty ageProperty() {
    return age;
  }

  public User getUser() {
    return user;
  }
}
