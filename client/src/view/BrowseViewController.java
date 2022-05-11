package view;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class BrowseViewController extends ViewController
{
  public TextField nameSearch;
  public ChoiceBox consoleSearch;
  public ChoiceBox esrbSearch;
  public TableView table;
  public TableColumn nameColumn;
  public TableColumn consoleColumn;
  public TableColumn producerColumn;
  public TableColumn esrbColumn;
  public Label error;

  @Override protected void init()
  {

  }

  public void searchButton(ActionEvent actionEvent)
  {
  }

  public void info(ActionEvent actionEvent) {
    // Use selected game
    getViewHandler().openView("GameInfoView.fxml");
  }

  public void rent(ActionEvent actionEvent) {
    // TODO: 11/05/2022 Confirmation window with name of game
  }

  public void back(ActionEvent actionEvent) {
    getViewHandler().openView("UserProfileView.fxml");
  }
}
