package view;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

  @Override protected void init()
  {

  }

  public void searchButton(ActionEvent actionEvent)
  {
  }
}
