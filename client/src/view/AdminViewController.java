package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AdminViewController extends ViewController
{

  @FXML public TextField registeredUsers;
  @FXML public TextField totalGames;
  @FXML public TextField rentedGames;
  @FXML public TextField recentGame;
  @FXML public Label errorLabel;

  @Override protected void init()
  {
    registeredUsers.textProperty()
        .bind(getViewModelFactory().getAdminViewModel().getRegisteredUsers());
    totalGames.textProperty()
        .bind(getViewModelFactory().getAdminViewModel().getRentedGames());
    rentedGames.textProperty()
        .bind(getViewModelFactory().getAdminViewModel().getRentedGames());
    recentGame.textProperty()
        .bind(getViewModelFactory().getAdminViewModel().getRecentGame());

    errorLabel.textProperty()
        .bind(getViewModelFactory().getAdminViewModel().getErrorLabel());
    getViewModelFactory().getAdminViewModel().reset();
  }

  @FXML public void manageUsers(ActionEvent actionEvent)
  {
    getViewHandler().openView("UserListView.fxml");
  }

  @FXML public void transactions(ActionEvent actionEvent)
  {
    // Open view for transactions goes here
  }

  @FXML public void manageGames(ActionEvent actionEvent)
  {
    getViewHandler().openView("Inventory.fxml");
  }
}
