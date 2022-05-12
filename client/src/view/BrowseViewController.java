package view;

import Model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import viewmodel.BrowseViewModel;
import viewmodel.SimpleGameViewModel;

public class BrowseViewController extends ViewController {
  public TextField nameSearch;
  public ChoiceBox<String> consoleSearch;
  public ChoiceBox<String> esrbSearch;
  public TableView<SimpleGameViewModel> table;
  public TableColumn<SimpleGameViewModel, String> nameColumn;
  public TableColumn<SimpleGameViewModel, String> consoleColumn;
  public TableColumn<SimpleGameViewModel, String> producerColumn;
  public TableColumn<SimpleGameViewModel, String> esrbColumn;
  public Label error;
  private BrowseViewModel viewModel;
  // Test values
  final ObservableList<SimpleGameViewModel> data = FXCollections.observableArrayList(
          new SimpleGameViewModel(new Game("TestName", "TestProducer", "PC", "E")),
          new SimpleGameViewModel(new Game("TestName2", "TestProducer2", "PC", "E")));

  @Override
  protected void init() {
    consoleSearch.getItems().addAll("PC", "Xbox", "PlayStation");
    esrbSearch.getItems().addAll("E", "E10+", "T", "M", "AO");
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    consoleColumn.setCellValueFactory(cellData -> cellData.getValue().getConsole());
    producerColumn.setCellValueFactory(cellData -> cellData.getValue().getProducer());
    esrbColumn.setCellValueFactory(cellData -> cellData.getValue().getEsrbProperty());
    table.setItems(data);
    viewModel = getViewModelFactory().getBrowseViewModel();
    table.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldV, newV) -> this.viewModel.setSelectedGameProperty(newV));;
    reset();
  }

  public void reset() {
    error.setText("");
    table.getSelectionModel().clearSelection();
  }

  public void searchButton() {
    // Use these values to search the games and reset the table
    String name = nameSearch.getText();
    String console = consoleSearch.getValue();
    String esrb = esrbSearch.getValue();
  }

  public void info() {
    // Use selected game
    getViewHandler().openView("GameInfoView.fxml");
  }

  public void rent() {
    // TODO: 11/05/2022 Confirmation window with name of game
  }

  public void back() {
    getViewHandler().openView("UserProfileView.fxml");
  }
}
