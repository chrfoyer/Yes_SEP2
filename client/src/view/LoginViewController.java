package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import viewmodel.LoginViewModel;

public class LoginViewController extends ViewController
{
  public TextField username;
  public TextField password;
  public Label errorLabel;

  private LoginViewModel loginViewModel = getViewModelFactory().getLoginViewModel();

  @Override protected void init()
  {
    username.textProperty()
        .bindBidirectional(loginViewModel.getUsernameProperty());
    password.textProperty()
        .bindBidirectional(loginViewModel.getPasswordProperty());
    errorLabel.textProperty().bind(loginViewModel.getErrorLabel());
  }

  public void login(ActionEvent actionEvent)
  {
  }

  public void signup(ActionEvent actionEvent)
  {
  }
}
