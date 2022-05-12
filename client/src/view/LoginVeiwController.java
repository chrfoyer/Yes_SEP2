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

  /**
   * initializes the variables and binds them
   */
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

  /**
   * method for figuring out if user is admin and whether to open the admin view or the user profile view. It also resets the viewmodel.
   */
  public void login()
  {
    if (getViewModelFactory().getLoginViewModel().login())
    {
      if (LoginViewModel.currentlyLoggedInUser.isAdmin())
      {
        getViewModelFactory().getAdminViewModel().reset();
        getViewHandler().openView("AdminView.fxml");

        getViewModelFactory().getLoginViewModel().reset();
      }
      else
      {
        getViewHandler().openView("UserProfileView.fxml");

        getViewModelFactory().getLoginViewModel().reset();
        getViewModelFactory().getUserProfileViewModel().reset();
      }
    }
  }

  /**
   * opens the signup view and resets the view model
   */
  public void signup()
  {
    getViewHandler().openView("SignupView.fxml");
    getViewModelFactory().getLoginViewModel().reset();
  }

  /**
   * logs the user in
   */
  public void onEnter()
  {
    this.login();
  }
}
