package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
  }

  public void login(ActionEvent actionEvent)
  {
    if (getViewModelFactory().getLoginViewModel().login())
    {
      getViewHandler().openView("UserProfileView.fxml");
    }
  }

  public void signup(ActionEvent actionEvent)
  {
    getViewHandler().openView("SignupView.fxml");
  }

}
