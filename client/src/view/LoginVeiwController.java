package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebView;
import mediator.CurrentlyLoggedUser;
import viewmodel.LoginViewModel;

import java.awt.*;
import java.net.URL;
import java.util.Optional;
import java.util.Scanner;

// When this is spelled correctly, things break. This is the way.\

/*
███╗░░██╗░█████╗░░░░░░░░░████████╗░█████╗░██╗░░░██╗░█████╗░██╗░░██╗
████╗░██║██╔══██╗░░░░░░░░╚══██╔══╝██╔══██╗██║░░░██║██╔══██╗██║░░██║
██╔██╗██║██║░░██║░░░░░░░░░░░██║░░░██║░░██║██║░░░██║██║░░╚═╝███████║
██║╚████║██║░░██║░░░░░░░░░░░██║░░░██║░░██║██║░░░██║██║░░██╗██╔══██║
██║░╚███║╚█████╔╝░░░░░░░░░░░██║░░░╚█████╔╝╚██████╔╝╚█████╔╝██║░░██║
╚═╝░░╚══╝░╚════╝░░░░░░░░░░░░╚═╝░░░░╚════╝░░╚═════╝░░╚════╝░╚═╝░░╚═╝
 */
public class LoginVeiwController extends ViewController {
  public TextField username;
  public TextField password;
  public Label errorLabel;
  private LoginViewModel viewModel;

  /**
   * initializes the variables and binds them
   */
  @Override
  protected void init() {
    viewModel = getViewModelFactory().getLoginViewModel();
    username.textProperty().bindBidirectional(viewModel.getUsernameProperty());
    password.textProperty().bindBidirectional(viewModel.getPasswordProperty());
    errorLabel.textProperty().bind(viewModel.getErrorLabel());
    CurrentlyLoggedUser.logout();
    reset();
  }

  public void reset() {
    viewModel.reset();
  }

  /**
   * method for figuring out if user is admin and whether to open the admin view or the user profile view. It also
   * resets the viewmodel.
   */
  public void login() {
    // TODO: 2022. 05. 18. lev testing do not remove pls
    /*
    try
    {
      URL url=new URL("https://howlongtobeat.com/game?id=6064");
      Scanner scanner=new Scanner(url.openStream());

      StringBuffer stringBuffer=new StringBuffer();
      while (scanner.hasNext())
      {
        stringBuffer.append(scanner.next());
      }
      String result=stringBuffer.toString();
      result = result.replaceAll("<[^>]*>", "");
      result.contains("a");


    }
    catch (Exception e)
    {
      errorLabel.setText(e.getMessage());
    }
     */


    if (getViewModelFactory().getLoginViewModel().login()) {
      if (CurrentlyLoggedUser.isAdmin()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to log in as an administrator?");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
          getViewHandler().openView("AdminView.fxml");
          getViewModelFactory().getAdminViewModel().reset();
        }


      } else {
        getViewHandler().openView("UserProfileView.fxml");

        getViewModelFactory().getLoginViewModel().reset();
        getViewModelFactory().getUserProfileViewModel().reset();
      }
    }
  }

  /**
   * opens the signup view and resets the view model
   */
  public void signup() {
    getViewHandler().openView("SignupView.fxml");
    getViewModelFactory().getLoginViewModel().reset();
  }

  /**
   * logs the user in
   */
  public void onEnter() {
    this.login();
  }
}
