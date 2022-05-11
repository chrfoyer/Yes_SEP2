package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class UserEditViewController extends ViewController{
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

  @Override
  protected void init() {

  }

  @FXML
  public void cancel(ActionEvent actionEvent) {
  }

  @FXML
  public void apply(ActionEvent actionEvent) {
  }
}
