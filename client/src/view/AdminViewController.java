package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import viewmodel.AdminViewModel;

public class AdminViewController extends ViewController {

  @FXML
  public TextField registeredUsers;
  @FXML
  public TextField totalGames;
  @FXML
  public TextField rentedGames;
  @FXML
  public TextField recentGame;
  @FXML
  public Label errorLabel;
  private AdminViewModel viewModel;

  /**
   * method for initializing all the variables and binding them
   */
  @Override
  protected void init() {
    viewModel = getViewModelFactory().getAdminViewModel();
    registeredUsers.textProperty()
            .bind(viewModel.getRegisteredUsers());
    totalGames.textProperty()
            .bind(viewModel.getTotalGames());
    rentedGames.textProperty()
            .bind(viewModel.getRentedGames());
    recentGame.textProperty()
            .bind(viewModel.getRecentGame());

    errorLabel.textProperty()
            .bind(viewModel.getErrorLabel());
    reset();
  }

  public void reset() {
    viewModel.reset();
  }

  /**
   * method for opening UserlistView
   *
   * @param actionEvent manageUsers button pressed
   */
  @FXML
  public void manageUsers(ActionEvent actionEvent) {
    getViewHandler().openView("UserListView.fxml");
  }

  /**
   * method for opening transaction view
   *
   * @param actionEvent transactions button pressed
   */
  @FXML
  public void transactions(ActionEvent actionEvent) {
    // Open view for transactions goes here
  }

  /**
   * method for opening inventory
   *
   * @param actionEvent manageGames button pressed
   */
  @FXML
  public void manageGames(ActionEvent actionEvent) {
    getViewModelFactory().getInventoryViewModel().reset();
    getViewHandler().openView("InventoryView.fxml");
  }
}
