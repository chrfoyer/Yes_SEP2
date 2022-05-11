package view;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SignupViewController extends ViewController
{
  public TextField fullName;
  public DatePicker dob;    // TODO: 11.5.2022. HOW TO BIND THIS SHIT!!! 
  public TextField address;
  public TextField email;
  public TextField username;
  public TextField confirmPassword;
  public TextField password;

  @Override protected void init()
  {
    fullName.textProperty().bindBidirectional(
        getViewModelFactory().getSignupViewModel().getNameProperty());
    address.textProperty().bindBidirectional(
        getViewModelFactory().getSignupViewModel().getAddressProperty());
    email.textProperty().bindBidirectional(
        getViewModelFactory().getSignupViewModel().getEmailProperty());
    username.textProperty().bindBidirectional(
        getViewModelFactory().getSignupViewModel().getUsernameProperty());
    password.textProperty().bindBidirectional(
        getViewModelFactory().getSignupViewModel().getPasswordProperty());
    confirmPassword.textProperty().bindBidirectional(
        getViewModelFactory().getSignupViewModel().getConfirmProperty());
  }

  public void signup(ActionEvent actionEvent)
  {
    getViewModelFactory().getSignupViewModel().signup();
    getViewHandler().openView("LoginView.fxml");
  }
}
