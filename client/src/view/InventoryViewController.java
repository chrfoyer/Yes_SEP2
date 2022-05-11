package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InventoryViewController extends ViewController{
  @FXML
  public TableView table;
  @FXML
  public TableColumn nameColumn;
  @FXML
  public TableColumn timeColumn;
  @FXML
  public TableColumn dateAddedColumn;

  @Override
  protected void init() {

  }

  @FXML
  public void add(ActionEvent actionEvent) {
  }

  @FXML
  public void edit(ActionEvent actionEvent) {

  }

  @FXML
  public void remove(ActionEvent actionEvent) {
    // TODO: 11/05/2022 Add confirmation window with the name of the game.
  }
}
