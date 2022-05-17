package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import viewmodel.UserEditViewModel;

public class UserEditViewController extends ViewController {
  @FXML
  public TextField fullName;
  @FXML
  public DatePicker dob;
  @FXML
  public TextField address;
  @FXML
  public TextField email;
  @FXML
  public TextField username;
  @FXML
  public TextField password;
  @FXML
  public TextField confirmPassword;

  public UserEditViewModel viewModel;
  public Label error;

  /**
   * method for initializing all the variables and binding them
   */
  @Override
  protected void init() {
    viewModel = getViewModelFactory().getUserEditViewModel();
    fullName.textProperty().bindBidirectional(viewModel.getNameProperty());
    dob.valueProperty().bindBidirectional(viewModel.getDobProperty());
    address.textProperty().bindBidirectional(viewModel.getAddressProperty());
    email.textProperty().bindBidirectional(viewModel.getEmailProperty());
    username.textProperty().bindBidirectional(viewModel.getUsernameProperty());
    password.textProperty().bindBidirectional(viewModel.getPasswordProperty());
    confirmPassword.textProperty().bindBidirectional(
        viewModel.getConfirmProperty());
    error.textProperty().bind(viewModel.getErrorLabel());
  }

  /**
   * method for canceling and opening the UserlistView
   *
   * @param actionEvent cancel button pressed
   */
  @FXML
  public void cancel(ActionEvent actionEvent) {
    getViewHandler().openView("UserListView.fxml");

  }

  /**
   * method for applying the user edit changes
   *
   * @param actionEvent apply button pressed
   */
  @FXML
  public void apply(ActionEvent actionEvent) {
    // TODO: 11/05/2022 Actually edit the user info
    viewModel.editUser();
    getViewHandler().openView("UserListView.fxml");
  }
}
