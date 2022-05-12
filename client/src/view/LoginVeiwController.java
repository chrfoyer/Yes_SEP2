package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import viewmodel.LoginViewModel;

// When this is spelled correctly, things break. This is the way.\

/*
███╗░░██╗░█████╗░░░░░░░░░████████╗░█████╗░██╗░░░██╗░█████╗░██╗░░██╗
████╗░██║██╔══██╗░░░░░░░░╚══██╔══╝██╔══██╗██║░░░██║██╔══██╗██║░░██║
██╔██╗██║██║░░██║░░░░░░░░░░░██║░░░██║░░██║██║░░░██║██║░░╚═╝███████║
██║╚████║██║░░██║░░░░░░░░░░░██║░░░██║░░██║██║░░░██║██║░░██╗██╔══██║
██║░╚███║╚█████╔╝░░░░░░░░░░░██║░░░╚█████╔╝╚██████╔╝╚█████╔╝██║░░██║
╚═╝░░╚══╝░╚════╝░░░░░░░░░░░░╚═╝░░░░╚════╝░░╚═════╝░░╚════╝░╚═╝░░╚═╝
 */
public class LoginVeiwController extends ViewController
{
  public TextField username;
  public TextField password;
  public Label errorLabel;

  @Override protected void init()
  {
    username.textProperty().bindBidirectional(
        getViewModelFactory().getLoginViewModel().getUsernameProperty());
    password.textProperty().bindBidirectional(
        getViewModelFactory().getLoginViewModel().getPasswordProperty());
    errorLabel.textProperty()
        .bind(getViewModelFactory().getLoginViewModel().getErrorLabel());
    getViewModelFactory().getLoginViewModel().reset();
    LoginViewModel.logout();
  }

  public void login()
  {
    if (getViewModelFactory().getLoginViewModel().login())
    {
      getViewHandler().openView("UserProfileView.fxml");
      getViewModelFactory().getLoginViewModel().reset();
      getViewModelFactory().getUserProfileViewModel().reset();
    }
  }

  public void signup()
  {
    getViewHandler().openView("SignupView.fxml");
    getViewModelFactory().getLoginViewModel().reset();
  }

  public void onEnter()
  {
    this.login();
  }
}
