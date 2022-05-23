package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mediator.CurrentlyLoggedUser;
import viewmodel.AdminViewModel;

import java.util.Optional;

/**
 * controller for the AdminView
 * @author Chris, Martin, Levente, Kruno
 * @version 0.4 19/5/22
 */
public class AdminViewController extends ViewController
{

    @FXML
    public TextField registeredUsers;
    @FXML
    public TextField totalGames;
    @FXML
    public TextField rentedGames;
    @FXML
    public TextField recentGame;
    @FXML
    public Label errorLabel;
    private AdminViewModel viewModel;

    /**
     * method for initializing all the variables and binding them
     */
    @Override
    protected void init()
    {
        viewModel = getViewModelFactory().getAdminViewModel();
        registeredUsers.textProperty().bind(viewModel.getRegisteredUsers());
        totalGames.textProperty().bind(viewModel.getTotalGames());
        rentedGames.textProperty().bind(viewModel.getRentedGames());
        recentGame.textProperty().bind(viewModel.getRecentGame());

        errorLabel.textProperty().bind(viewModel.getErrorLabel());
        reset();
    }

    /**
     * method that resets the fields in the view
     */
    public void reset()
    {
        viewModel.reset();
    }

    /**
     * method for opening UserlistView
     */
    @FXML
    public void manageUsers()
    {
        getViewHandler().openView("UserListView.fxml");
    }

    /**
     * method for opening transaction view
     */
    @FXML
    public void transactions()
    {
        getViewHandler().openView("TransactionsView.fxml");
    }

    /**
     * method for opening inventory
     */
    @FXML
    public void manageGames()
    {
        getViewModelFactory().getInventoryViewModel().reset();
        getViewHandler().openView("InventoryView.fxml");
    }

    /**
     * method for the logout button, logging a user out and opening the LoginView
     */
    public void logout()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to log out?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK)
        {
            getViewHandler().openView("LoginView.fxml");
            CurrentlyLoggedUser.logout();
        }
    }
}
