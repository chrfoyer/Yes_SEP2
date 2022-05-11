package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginVeiwController extends ViewController
{
  public TextField username;
  public TextField password;
  public Label errorLabel;

  @Override protected void init()
  {
    username.textProperty().bindBidirectional(getViewModelFactory().getLoginViewModel().getUsernameProperty());
    password.textProperty().bindBidirectional(getViewModelFactory().getLoginViewModel().getPasswordProperty());
    errorLabel.textProperty().bind(getViewModelFactory().getLoginViewModel().getErrorLabel());
  }

  public void login(ActionEvent actionEvent)
  {
  }

  public void signup(ActionEvent actionEvent)
  {
  }
}
