package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import viewmodel.AddEditGameViewModel;
import viewmodel.InventoryViewModel;
import viewmodel.SimpleGameViewModel;

public class InventoryViewController extends ViewController
{
  @FXML public TableView<SimpleGameViewModel> table;
  @FXML public TableColumn<SimpleGameViewModel, String> nameColumn;
  @FXML public TableColumn<SimpleGameViewModel, String> timeColumn;
  @FXML public TableColumn<SimpleGameViewModel, String> dateAddedColumn;
  @FXML public Label error;
  private InventoryViewModel viewModel;
  private AddEditGameViewModel gameViewModel;

  @Override protected void init()
  {
    nameColumn.setCellValueFactory(
        cellData -> cellData.getValue().getNameProperty());
    timeColumn.setCellValueFactory(
        cellData -> cellData.getValue().getTimeProperty().asString());
    //todo replace with dateAdded
    dateAddedColumn.setCellValueFactory(
        cellData -> cellData.getValue().getProducer());
    viewModel = getViewModelFactory().getInventoryViewModel();
    table.setItems(viewModel.getList());

    gameViewModel = getViewModelFactory().getAddEditGameViewModel();

    error.textProperty().bind(viewModel.getError());
    table.getSelectionModel().selectedItemProperty().addListener(
        (obs, oldV, newV) -> gameViewModel.setSelectedGameProperty(newV));
    reset();
  }

  @FXML public void add(ActionEvent actionEvent)
  {
  }

  @FXML public void edit(ActionEvent actionEvent)
  {
    gameViewModel.reset();
    getViewHandler().openView("AddEditGame.fxml");
  }

  @FXML public void remove(ActionEvent actionEvent)
  {
    // TODO: 11/05/2022 Add confirmation window with the name of the game.
  }
}
