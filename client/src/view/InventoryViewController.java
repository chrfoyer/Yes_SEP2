package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import viewmodel.AddEditGameViewModel;
import viewmodel.InventoryViewModel;
import viewmodel.SimpleGameViewModel;

import java.util.Optional;

public class InventoryViewController extends ViewController
{
  @FXML
  public TableView<SimpleGameViewModel> table;
  @FXML
  public TableColumn<SimpleGameViewModel, String> nameColumn;
  @FXML
  public TableColumn<SimpleGameViewModel, String> rentedColumn;
  @FXML
  public TableColumn<SimpleGameViewModel, String> consoleColumn;
  @FXML
  public TableColumn<SimpleGameViewModel, String> esrbColumn;
  @FXML
  public TableColumn<SimpleGameViewModel, String> producerColumn;
  @FXML
  public TableColumn<SimpleGameViewModel, String> dateAddedColumn;

  public Label error;
  private InventoryViewModel viewModel;
  private AddEditGameViewModel gameViewModel;

  @Override
  protected void init()
  {
    nameColumn.setCellValueFactory(
            cellData -> cellData.getValue().getNameProperty());
    rentedColumn.setCellValueFactory(
            cellData -> cellData.getValue().getRentedStringProperty());
    consoleColumn.setCellValueFactory(
            cellData -> cellData.getValue().getConsole());
    esrbColumn.setCellValueFactory(
            cellData -> cellData.getValue().getEsrbProperty());
    producerColumn.setCellValueFactory(
            cellData -> cellData.getValue().getProducer());
    dateAddedColumn.setCellValueFactory(
            cellData -> cellData.getValue().getDateAdded().asString());
    viewModel = getViewModelFactory().getInventoryViewModel();
    table.setItems(viewModel.getList());

    gameViewModel = getViewModelFactory().getAddEditGameViewModel();

    error.textProperty().bind(viewModel.getError());
    table.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldV, newV) -> gameViewModel.setSelectedGameProperty(newV));
    reset();
  }

  public void reset()
  {
    table.getSelectionModel().clearSelection();
    viewModel.reset();
    gameViewModel.reset();
  }

  @FXML
  public void add(ActionEvent actionEvent)
  {
  }

  @FXML
  public void addEdit(ActionEvent actionEvent)
  {

    gameViewModel.reset();

    getViewHandler().openView("AddEditGame.fxml");
  }

  @FXML
  public void cancel(ActionEvent actionEvent)
  {
    getViewModelFactory().getAdminViewModel().reset();
    getViewHandler().openView("AdminView.fxml");
  }

  @FXML
  public void remove(ActionEvent actionEvent)
  {
    // TODO: 11/05/2022 Add confirmation window with the name of the game.

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to delete the game? -> "
                    + gameViewModel.getSelectedGameProperty().getGame().getName());
    Optional<ButtonType> option = alert.showAndWait();
    if (option.get() == ButtonType.OK)
    {
      viewModel.setSelectedGameProperty(
              gameViewModel.getSelectedGameProperty());
      viewModel.removeGame();
    }
  }
}
