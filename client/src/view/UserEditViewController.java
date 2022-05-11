package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class UserEditViewController {
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

  @FXML
  public void cancel(ActionEvent actionEvent) {
  }

  @FXML
  public void apply(ActionEvent actionEvent) {
  }
}
