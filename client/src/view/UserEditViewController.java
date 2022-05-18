package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import viewmodel.UserEditViewModel;

import java.util.Optional;

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
  public Label balance;
  public TextField fineRefundAmount;
  public Label hasSubscription;

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
    confirmPassword.textProperty()
            .bindBidirectional(viewModel.getConfirmProperty());
    error.textProperty().bind(viewModel.getErrorLabel());
    balance.textProperty().bind(viewModel.getBalanceLabel());
    fineRefundAmount.textProperty()
            .bindBidirectional(viewModel.getFineRefundProperty());
    hasSubscription.textProperty().bind(viewModel.getHasSubscriptionProperty());

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
    getViewModelFactory().getUserListViewModel().reset();
    getViewHandler().openView("UserListView.fxml");

  }

  public void fine_refund(ActionEvent actionEvent) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to revoke this user's subscription?");
    Optional<ButtonType> option = alert.showAndWait();
    if (option.get() == ButtonType.OK) {
      viewModel.fineRefund();
      getViewModelFactory().getUserListViewModel().reset();
      getViewHandler().openView("UserListView.fxml");
    }
  }

  public void banHammer(ActionEvent actionEvent) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to revoke this user's subscription?");
    Optional<ButtonType> option = alert.showAndWait();
    if (option.get() == ButtonType.OK) {
      viewModel.revokeSubscription();
      getViewModelFactory().getUserListViewModel().reset();
      getViewHandler().openView("UserListView.fxml");
    }
  }
}
