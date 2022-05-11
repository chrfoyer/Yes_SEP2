package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AdminViewController extends ViewController{


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

  @Override
  protected void init() {

  }

  @FXML
  public void manageUsers(ActionEvent actionEvent) {
  }

  @FXML
  public void transactions(ActionEvent actionEvent) {
  }

  @FXML
  public void manageGames(ActionEvent actionEvent) {
  }
}
