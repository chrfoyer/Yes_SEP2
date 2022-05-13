package view;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import viewmodel.SignupViewModel;

public class SignupViewController extends ViewController {
  public TextField fullName;
  public DatePicker dob;    // TODO: 11.5.2022. HOW TO BIND THIS SHIT!!! 
  public TextField address;
  public TextField email;
  public TextField username;
  public TextField confirmPassword;
  public TextField password;
  public Label error;
  private SignupViewModel viewModel;

  /**
   * initializes all the variables and binds them
   */
  @Override
  protected void init() {
    viewModel = getViewModelFactory().getSignupViewModel();
    fullName.textProperty().bindBidirectional(
            viewModel.getNameProperty());
    address.textProperty().bindBidirectional(
            viewModel.getAddressProperty());
    email.textProperty().bindBidirectional(
            viewModel.getEmailProperty());
    username.textProperty().bindBidirectional(
            viewModel.getUsernameProperty());
    password.textProperty().bindBidirectional(
            viewModel.getPasswordProperty());
    confirmPassword.textProperty().bindBidirectional(
            viewModel.getConfirmProperty());
    error.textProperty()
            .bind(viewModel.getErrorLabel());
    reset();
    dob.valueProperty()
            .bindBidirectional(viewModel.getDobProperty());
  }

  public void reset() {
    viewModel.reset();
  }

  /**
   * methodd for signing up
   *
   * @param actionEvent when clicking the signup button
   */
  public void signup(ActionEvent actionEvent) {
    if (getViewModelFactory().getSignupViewModel().signup()) {
      getViewHandler().openView("LoginView.fxml");
      getViewModelFactory().getSignupViewModel().reset();
    }

  }

  /**
   * method for canceling the signupview
   *
   * @param actionEvent button for canceling signupview
   */
  public void cancel(ActionEvent actionEvent) {
    getViewHandler().openView("LoginView.fxml");
    getViewModelFactory().getSignupViewModel().reset();
  }
}
