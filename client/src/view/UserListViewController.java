package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserListViewController {
  @FXML public TableColumn usernameColumn;
  @FXML public TableColumn balanceColumn;
  @FXML public TableColumn dateJoined;
  @FXML public TableView table;


  @FXML public void remove(ActionEvent actionEvent) {
  }

  @FXML public void edit(ActionEvent actionEvent) {
  }
}
