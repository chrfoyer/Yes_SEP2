package view;

import Model.Game;
import javafx.scene.control.*;
import mediator.CurrentlyLoggedUser;
import viewmodel.BrowseViewModel;
import viewmodel.GameInfoViewModel;
import viewmodel.SimpleGameViewModel;

import java.rmi.RemoteException;
import java.util.Optional;


/**
 * controller for the Browse View
 */
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
    @Override
    protected void init()
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
        if (CurrentlyLoggedUser.getLoggedInUser().hasSubscription())
        {SimpleGameViewModel temp = table.getSelectionModel().getSelectedItem();
            Game selectedGame = temp.getGame();

            if(browseViewModel.ageCheck(selectedGame))
            {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to rent game? -> " + selectedGame.getName());
                Optional<ButtonType> option = alert.showAndWait();
                if (option.isPresent() && option.get() == ButtonType.OK)
                {
                    if (browseViewModel.rentGame(selectedGame))
                    {


                        //displaying confirmation message
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Congratulations, Game rented");
                        alert.setContentText("Now you have the game (" + selectedGame.getName()
                                + ") for 14 days\n"
                                + "If you would like to extend it you may do that on your profile!");
                        alert.showAndWait();
                        browseViewModel.reset();
                    }
                }
            }
         //   browseViewModel.reset();
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "You cannot rent games without an active subscription!");
            alert.showAndWait();
        }

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
