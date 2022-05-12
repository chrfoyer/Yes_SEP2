package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserListViewController extends ViewController{
  @FXML public TableColumn usernameColumn;
  @FXML public TableColumn balanceColumn;
  @FXML public TableColumn dateJoined;
  @FXML public TableView table;
  @FXML public Label error;

  @Override
  protected void init() {

  }

  @FXML public void remove(ActionEvent actionEvent) {
    // Remove the user
  }

  /**
   * method for opening UserEditView
   * @param actionEvent edit button pressed
   */
  @FXML public void edit(ActionEvent actionEvent) {
    // Must use the selected user
    getViewHandler().openView("UserEditView.fxml");
  }
}
