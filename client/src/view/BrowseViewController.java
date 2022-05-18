package view;

import Model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import viewmodel.BrowseViewModel;
import viewmodel.GameInfoViewModel;
import viewmodel.SimpleGameViewModel;

import java.rmi.RemoteException;
import java.util.Optional;

public class BrowseViewController extends ViewController
{
  public TextField nameSearch;
  public ChoiceBox<String> consoleSearch;
  public ChoiceBox<String> esrbSearch;
  public TableView<SimpleGameViewModel> table;
  public TableColumn<SimpleGameViewModel, String> nameColumn;
  public TableColumn<SimpleGameViewModel, String> consoleColumn;
  public TableColumn<SimpleGameViewModel, String> producerColumn;
  public TableColumn<SimpleGameViewModel, String> esrbColumn;
  public Label error;
  private BrowseViewModel browseViewModel;
  private GameInfoViewModel gameInfoViewModel;
  // Test values

  /**
   * method initializing all the variables and cells
   */
  @Override protected void init()
  {
    consoleSearch.getItems().addAll("PC", "Xbox", "PlayStation");
    esrbSearch.getItems().addAll("E", "E10+", "T", "M", "AO");
    consoleSearch.setValue("PC");
    esrbSearch.setValue("E");
    nameColumn.setCellValueFactory(
        cellData -> cellData.getValue().getNameProperty());
    consoleColumn.setCellValueFactory(
        cellData -> cellData.getValue().getConsole());
    producerColumn.setCellValueFactory(
        cellData -> cellData.getValue().getProducer());
    esrbColumn.setCellValueFactory(
        cellData -> cellData.getValue().getEsrbProperty());
    browseViewModel = getViewModelFactory().getBrowseViewModel();
    gameInfoViewModel = getViewModelFactory().getGameInfoViewModel();
    table.setItems(browseViewModel.getData());
    error.textProperty().bind(browseViewModel.getErrorLabel());
    // Sets the selected game in the game info view model
    table.getSelectionModel().selectedItemProperty().addListener(
        (obs, oldV, newV) -> gameInfoViewModel.setSelectedGameProperty(newV));
  }

  /**
   * method that resets the fields in the view
   */
  public void reset()
  {
    table.getSelectionModel().clearSelection();
    browseViewModel.reset();
    gameInfoViewModel.reset();
  }

  /**
   * Logic for the button that opens the GameInfoView
   */
  public void searchButton()
  {
    // TODO: 12/05/2022 search functionality
    // Use these values to search the games and reset the table
    String name = nameSearch.getText();
    String console = consoleSearch.getValue();
    String esrb = esrbSearch.getValue();
  }

  /**
   * Logic for the button that opens the GameInfoView
   */
  public void info()
  {
    gameInfoViewModel.reset();
    getViewHandler().openView("GameInfoView.fxml");
  }

  /**
   * Logic for the button that handles renting of the game
   */
  public void rent() throws RemoteException
  {
    // TODO: 11/05/2022 Confirmation window with name of game
    SimpleGameViewModel temp = table.getSelectionModel().getSelectedItem();
    browseViewModel.rentGame(temp);

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
        "" + temp.getNameProperty().get()
            + " - " + temp.getTimeProperty().get());
    alert.showAndWait();

    Game debug=table.getSelectionModel().getSelectedItem().getGame();

    getViewModelFactory().getUserProfileViewModel()
        .rentGame(table.getSelectionModel().getSelectedItem());
    browseViewModel.reset();
  }

  /**
   * Logic for the button that opens the UserProfileView
   */
  public void back()
  {
    getViewModelFactory().getUserProfileViewModel().reset();
    getViewHandler().openView("UserProfileView.fxml");
  }
}
